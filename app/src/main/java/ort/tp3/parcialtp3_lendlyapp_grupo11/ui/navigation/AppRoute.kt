package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

object AppRoute {
    const val SPLASH = "splash"
    const val ONBOARDING_FLOW = "onboarding_flow"
    
    const val LOGIN = "login"
    const val VERIFY_PHONE_NUMBER = "verify_phone_number"
    const val SMS_VERIFICATION = "sms_verification"
    const val CREATE_PASSWORD = "create_password"
    const val ID_VERIFICATION = "id_verification"
    const val FACE_RECOGNITION = "face_recognition"
    const val SIGNATURE = "signature"
    const val PROFILE_DETAIL_FORM = "profile_detail_form"
    const val VERIFIED = "verified"
    const val DONE = "done"

    const val HOME = "home"
    const val LOAN = "loan"
    const val LOAN_APPLY = "loan_apply"
    const val LOAN_SUCCESS = "loan_success"
    const val LOAN_HISTORY = "loan_history"
    const val LOAN_PAYMENT_SUCCESS = "loan_payment_success"
    
    const val SHOP = "shop"
    const val SHOP_SEARCH = "shop_search"
    const val PRODUCT_DETAIL = "product_detail"
    const val FILTER = "filter"
    const val HISTORY = "history"
    const val TRANSACTION_DETAIL = "transaction_detail"
    const val NOTIFICATIONS = "notifications"
    
    const val CASH_IN_OPTIONS = "cash_in_options"
    const val ONLINE_CASH_IN_OPTIONS = "online_cash_in_options"
    const val OVER_THE_COUNTER_PARTNERS = "over_the_counter_partners"
    const val CASH_IN_AMOUNT = "cash_in_amount"
    const val CASH_IN_SUCCESS = "cash_in_success"

    const val MANAGE = "manage"
    const val MANAGE_DONE = "manage_done"
    const val PROFILE_DETAIL = "profile_detail"
    const val CREDIT_SCORE = "credit_score"

    const val TRANSACTION_DETAIL_WITH_ARG = "$TRANSACTION_DETAIL/{transactionId}"
    const val PRODUCT_DETAIL_WITH_ARG = "$PRODUCT_DETAIL/{productId}"

    fun transactionDetail(transactionId: String): String {
        return "$TRANSACTION_DETAIL/$transactionId"
    }

    fun productDetail(productId: String): String {
        return "$PRODUCT_DETAIL/$productId"
    }
}
