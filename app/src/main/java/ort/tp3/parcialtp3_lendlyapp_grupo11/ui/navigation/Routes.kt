package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

sealed class Screen(val route: String) {
    data object Splash      : Screen("splash")
    data object Onboarding1 : Screen("onboarding1")
    data object Onboarding2 : Screen("onboarding2")
    data object Onboarding3 : Screen("onboarding3")
    data object Login       : Screen("login")
    data object Signup      : Screen("signup")
    data object Home        : Screen("home")
    data object Loan        : Screen("loan")
    data object LoanApply   : Screen("loan_apply")
    data object LoanSuccess : Screen("loan_success")
    data object LoanHistory : Screen("loan_history")
}