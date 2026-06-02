package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val balance: String = "₱0.00",
    val loans: List<HomeLoanUi> = emptyList(),
    val products: List<HomeProductUi> = emptyList(),
    val error: String? = null
)

data class HomeLoanUi(
    val lender: String,
    val logoUrl: String,
    val amountDue: String,
    val feeLabel: String
)

data class HomeProductUi(
    val name: String,
    val imageUrl: String,
    val monthlyInstallment: String,
    val months: String
)
