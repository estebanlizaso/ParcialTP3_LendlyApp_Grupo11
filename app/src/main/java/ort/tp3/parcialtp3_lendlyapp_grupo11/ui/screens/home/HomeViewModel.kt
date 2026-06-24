package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val shopRepository: ShopRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        observeRecommendedProducts()
        loadHomeData()
    }

    private fun observeRecommendedProducts() {
        viewModelScope.launch {
            shopRepository.recommended.collectLatest { products ->
                uiState = uiState.copy(
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

                uiState = uiState.copy(
                    isLoading = false,
                    avatarUrl = userResponse.user.avatar,
                    balance = formatMoney(userResponse.user.availableBalance),
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
                uiState = uiState.copy(
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
