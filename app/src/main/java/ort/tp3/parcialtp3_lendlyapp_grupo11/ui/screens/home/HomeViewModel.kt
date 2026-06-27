package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val shopRepository: ShopRepository,
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val firestoreRepository: ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeUnreadNotifications()
        observeRecommendedProducts()
        observeUser()
        loadHomeData()
    }

    private fun observeUnreadNotifications() {
        viewModelScope.launch {
            sessionManager.unreadNotificationCount.collectLatest { count ->
                _uiState.value = _uiState.value.copy(notificationBadgeCount = count)
            }
        }
    }

    private fun observeUser() {
        val uid = sessionManager.getToken()
        if (uid != null) {
            sessionManager.refreshUnreadNotificationCount(uid)

            viewModelScope.launch {
                syncLocalUser(uid)

                // Sincronización desde Firestore al iniciar
                try {
                    val scoring = firestoreRepository.getUserScoring(uid)
                    scoring?.let {
                        userDao.updateAccountBalance(uid, it.availableBalance)
                        userDao.updateCreditScore(uid, it.creditScore)
                    }
                } catch (e: Exception) {
                    // Error de red o Firestore
                }

                userDao.getUserById(uid).collectLatest { user ->
                    user?.let {
                        _uiState.value = _uiState.value.copy(
                            balance = formatMoney(it.accountBalance),
                            avatarUrl = it.avatar
                        )
                    }
                }
            }
        }
    }

    private suspend fun syncLocalUser(uid: String) {
        try {
            val inputStream = context.assets.open("initial_users.json")
            val reader = InputStreamReader(inputStream)
            val users: List<UserEntity> = Gson().fromJson(reader, object : TypeToken<List<UserEntity>>() {}.type)
            reader.close()

            val currentUserData = users.find { it.uid == uid }
            if (currentUserData != null) {
                userDao.updateCreditScore(uid, currentUserData.creditScore)
                userDao.updateAvatar(uid, currentUserData.avatar)
            }
        } catch (e: Exception) {
            // Local seed sync is best effort; API data still loads below.
        }
    }

    private fun observeRecommendedProducts() {
        viewModelScope.launch {
            shopRepository.recommended.collectLatest { products ->
                _uiState.value = _uiState.value.copy(
                    products = products.map { product ->
                        HomeProductUi(
                            name = product.name,
                            imageUrl = product.image,
                            monthlyInstallment = formatMoney(product.monthlyInstallment),
                            months = "${product.installmentMonths} mo"
                        )
                    }
                )
            }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            try {
                val userResponse = repository.getUser()
                val loansResponse = repository.getLoans()
                val productsResponse = repository.getProducts()
                val brandLogos = productsResponse.brands.associate { brand ->
                    brand.name.lowercase() to brand.logo
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    balance = formatMoney(userResponse.user.availableBalance),
                    avatarUrl = userResponse.user.avatar,
                    loans = loansResponse.loans
                        .filter { it.status == "ACTIVE" }
                        .map { loan ->
                            val brandLogo = brandLogos.entries
                                .firstOrNull { (brandName, _) ->
                                    loan.lender.lowercase().contains(brandName)
                                }
                                ?.value

                            HomeLoanUi(
                                lender = loan.lender,
                                logoUrl = brandLogo ?: loan.lenderLogo,
                                amountDue = formatMoney(loan.amountDue),
                                feeLabel = loan.nextPaymentLabel.orEmpty()
                            )
                        }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo cargar el Home"
                )
            }
        }
    }

    private fun formatMoney(value: Double): String {
        return String.format(Locale.US, "₱%,.2f", value)
    }
}
