package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

object AppRoute {
    const val HOME = "home"
    const val HISTORY = "history"
    const val TRANSACTION_DETAIL = "transaction_detail"
    const val NOTIFICATIONS = "notifications"
    const val CASH_IN_OPTIONS = "cash_in_options"
    const val ONLINE_CASH_IN_OPTIONS = "online_cash_in_options"
    const val OVER_THE_COUNTER_PARTNERS = "over_the_counter_partners"
    const val CASH_IN_AMOUNT = "cash_in_amount"
    const val CASH_IN_SUCCESS = "cash_in_success"

    const val TRANSACTION_DETAIL_WITH_ARG = "$TRANSACTION_DETAIL/{transactionId}"

    fun transactionDetail(transactionId: String): String {
        return "$TRANSACTION_DETAIL/$transactionId"
    }
}
