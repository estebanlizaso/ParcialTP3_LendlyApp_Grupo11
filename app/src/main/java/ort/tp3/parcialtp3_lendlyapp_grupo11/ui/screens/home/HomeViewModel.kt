package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

class HomeViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHome()
    }

    private fun loadHome() {
        viewModelScope.launch {
            try {
                val userResponse = repository.getUser()
                val loansResponse = repository.getLoans()
                val productsResponse = repository.getProducts()
                val brandLogos = productsResponse.brands.associate { brand ->
                    brand.name.lowercase() to brand.logo
                }
                val featuredProducts = productsResponse.featured.map { product ->
                    HomeProductUi(
                        name = product.name,
                        imageUrl = product.image,
                        monthlyInstallment = formatMoney(product.monthlyInstallment),
                        months = "${product.installmentMonths} mo"
                    )
                }.toMutableList()

                val phoneProduct = featuredProducts.firstOrNull { product ->
                    product.name.contains("iphone", ignoreCase = true) || product.name.contains("phone", ignoreCase = true)
                } ?: HomeProductUi("iPhone 12 Pro", "", formatMoney(1200.0), "24 mo")
                val headphonesProduct = featuredProducts.firstOrNull { product ->
                    product.name.contains("airpods", ignoreCase = true) || product.name.contains("headphones", ignoreCase = true)
                } ?: HomeProductUi("AirPods Pro", "", formatMoney(1200.0), "24 mo")
                val shoesProduct = featuredProducts.firstOrNull { product ->
                    product.name.contains("nike", ignoreCase = true) || product.name.contains("shoe", ignoreCase = true) || product.name.contains("sneaker", ignoreCase = true)
                } ?: HomeProductUi("Nike Air Max", "", formatMoney(1200.0), "24 mo")
                val recommendedProducts = listOf(phoneProduct, headphonesProduct, shoesProduct)

                _uiState.value = HomeUiState(
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
                        },
                    products = recommendedProducts
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
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
