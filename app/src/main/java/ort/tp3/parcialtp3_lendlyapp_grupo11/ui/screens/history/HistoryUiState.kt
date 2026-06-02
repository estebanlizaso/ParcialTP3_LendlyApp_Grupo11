package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

data class HistoryUiState(
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

fun sampleHistoryUiState(): HistoryUiState {
    return HistoryUiState(
        avatarUrl = "https://i.pravatar.cc/150?img=3",
        todayTransactions = listOf(
            HistoryTransactionUi(
                id = "TXN-2026-0061",
                time = "9:07 AM",
                title = "Paid this month",
                merchant = "Apple Inc.",
                amount = "1,2555 PHP",
                type = HistoryTransactionType.PAID_BILL
            ),
            HistoryTransactionUi(
                id = "TXN-2026-0088",
                time = "9:07 AM",
                title = "Paid this month",
                merchant = "Apple Inc.",
                amount = "1,2555 PHP",
                type = HistoryTransactionType.PAID_BILL
            ),
            HistoryTransactionUi(
                id = "TXN-2026-0061",
                time = "9:07 AM",
                title = "Paid this month",
                merchant = "Apple Inc.",
                amount = "1,2555 PHP",
                type = HistoryTransactionType.PAID_BILL
            ),
            HistoryTransactionUi(
                id = "TXN-2026-0075",
                time = "9:07 AM",
                title = "Added",
                merchant = "",
                amount = "1,200 PHP",
                type = HistoryTransactionType.ADDED_BALANCE
            ),
            HistoryTransactionUi(
                id = "TXN-2026-0044",
                time = "9:07 AM",
                title = "Paid this month",
                merchant = "",
                amount = "1,200 PHP",
                type = HistoryTransactionType.ADDED_BALANCE
            )
        ),
        recentLoans = listOf(
            RecentLoanHistoryUi(
                date = "02/08/2024",
                productName = "iPhone 15 Pro Max",
                merchant = "Apple Inc.",
                status = "Paid"
            ),
            RecentLoanHistoryUi(
                date = "02/08/2024",
                productName = "iPhone 15 Pro Max",
                merchant = "Apple Inc.",
                status = "Paid"
            ),
            RecentLoanHistoryUi(
                date = "02/08/2024",
                productName = "iPhone 15 Pro Max",
                merchant = "Apple Inc.",
                status = "Paid"
            )
        )
    )
}
