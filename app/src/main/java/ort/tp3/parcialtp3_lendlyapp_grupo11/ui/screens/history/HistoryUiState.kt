package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

data class HistoryUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val avatarUrl: String = "",
    val filters: List<HistoryFilterUi> = HistoryFilterUi.entries,
    val todayTransactions: List<HistoryTransactionUi> = emptyList(),
    val recentLoans: List<RecentLoanHistoryUi> = emptyList()
)

enum class HistoryFilterUi(val label: String) {
    ALL("All"),
    TYPE("Type"),
    BALANCE("Balance"),
    PAID_BILLS("Paid Bills"),
    ADDED("Added")
}

enum class HistoryTransactionType {
    PAID_BILL,
    ADDED_BALANCE
}

data class HistoryTransactionUi(
    val id: String,
    val time: String,
    val title: String,
    val merchant: String,
    val amount: String,
    val type: HistoryTransactionType
)

data class RecentLoanHistoryUi(
    val date: String,
    val productName: String,
    val merchant: String,
    val status: String
)
