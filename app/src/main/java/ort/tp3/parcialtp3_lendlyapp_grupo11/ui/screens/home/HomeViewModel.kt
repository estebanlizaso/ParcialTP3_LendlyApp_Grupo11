package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.util.Locale
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

class HomeViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

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

                uiState = HomeUiState(
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
                    products = productsResponse.featured.map { product ->
                        HomeProductUi(
                            name = product.name,
                            imageUrl = product.image,
                            monthlyInstallment = formatMoney(product.monthlyInstallment),
                            months = "${product.installmentMonths} mo"
                        )
                    }
                )
            } catch (e: Exception) {
                uiState = HomeUiState(
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
