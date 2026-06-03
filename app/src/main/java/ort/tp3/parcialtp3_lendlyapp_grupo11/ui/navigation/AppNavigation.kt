package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.BottomNavBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInAmountScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInOptionsScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInSuccessScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.OnlineCashInOptionsScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.OverTheCounterPartnersScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.HistoryRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.TransactionDetailRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.HomeRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.NotificationScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan.LoanApplyScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan.LoanHistoryScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan.LoanScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan.LoanSuccessScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.CreatePasswordPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.DonePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.FaceRecognitionPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.IDVerificationPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.LoginPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.ProfileDetailFormPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.RegisterViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.SMSVerification
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.SignaturePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.VerifiedPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.VerifyPhoneNumber
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.CreditScorePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ManageDonePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ProfileDetailPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ProfilePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.onboarding.OnboardingScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.onboarding.SplashScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.FilterScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ProductDetailScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopSearchScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun AppNavigation(
    sessionManager: SessionManager,
    registerViewModel: RegisterViewModel,
    loanViewModel: LoanViewModel,
    cashInViewModel: CashInViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedNavIndex = selectedBottomNavIndex(currentRoute)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (selectedNavIndex >= 0) {
                Column(modifier = Modifier.padding(bottom = 18.dp)) {
                    BottomNavBar(
                        selectedRoute = when (currentRoute) {
                            AppRoute.HOME -> "Home"
                            AppRoute.LOAN -> "Loan"
                            AppRoute.SHOP -> "Shop"
                            AppRoute.HISTORY -> "History"
                            AppRoute.MANAGE -> "Manage"
                            else -> "Home"
                        },
                        onNavigate = { routeName ->
                            val route = when (routeName) {
                                "Home" -> AppRoute.HOME
                                "Loan" -> AppRoute.LOAN
                                "Shop" -> AppRoute.SHOP
                                "History" -> AppRoute.HISTORY
                                "Manage" -> AppRoute.MANAGE
                                else -> AppRoute.HOME
                            }
                            if (route != currentRoute) {
                                navController.navigate(route) {
                                    popUpTo(AppRoute.HOME) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.SPLASH,
            modifier = Modifier.padding(
                if (currentRoute == AppRoute.SPLASH || currentRoute == AppRoute.ONBOARDING_FLOW || currentRoute == null)
                    PaddingValues(0.dp)
                else
                    innerPadding
            )
        ) {

            composable(AppRoute.SPLASH) {
                SplashScreen(
                    onTimeout = {
                        if (sessionManager.isLoggedIn()) {
                            navController.navigate(AppRoute.HOME) {
                                popUpTo(AppRoute.SPLASH) { inclusive = true }
                            }
                        } else {
                            navController.navigate(AppRoute.ONBOARDING_FLOW) {
                                popUpTo(AppRoute.SPLASH) { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable(AppRoute.ONBOARDING_FLOW) {
                OnboardingScreen(
                    onLoginClick = { navController.navigate(AppRoute.LOGIN) },
                    onRegisterClick = { navController.navigate(AppRoute.VERIFY_PHONE_NUMBER) }
                )
            }

            composable(AppRoute.LOGIN) {
                LoginPage(
                    onLoginSuccess = {
                        navController.navigate(AppRoute.HOME) {
                            popUpTo(AppRoute.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(AppRoute.PROFILE_DETAIL_FORM) {
                ProfileDetailFormPage(
                    viewModel = registerViewModel,
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.SIGNATURE) }
                )
            }

            composable(AppRoute.ID_VERIFICATION) {
                IDVerificationPage(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.VERIFIED) }
                )
            }
            composable(AppRoute.FACE_RECOGNITION) {
                FaceRecognitionPage(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.ID_VERIFICATION) }
                )
            }
            composable(AppRoute.SIGNATURE) {
                SignaturePage(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.CREATE_PASSWORD) }
                )
            }
            composable(AppRoute.VERIFY_PHONE_NUMBER) {
                VerifyPhoneNumber(
                    onBackClick = { navController.popBackStack() },
                    onSendCodeClick = { navController.navigate(AppRoute.SMS_VERIFICATION) }
                )
            }
            composable(AppRoute.SMS_VERIFICATION) {
                SMSVerification(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.FACE_RECOGNITION) }
                )
            }
            composable(AppRoute.CREATE_PASSWORD) {
                CreatePasswordPage(
                    viewModel = registerViewModel,
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.DONE) }
                )
            }
            composable(AppRoute.VERIFIED) {
                VerifiedPage(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.PROFILE_DETAIL_FORM) }
                )
            }
            composable(AppRoute.DONE) {
                DonePage(
                    onExitClick = {
                        navController.navigate(AppRoute.HOME) {
                            popUpTo(AppRoute.HOME) { inclusive = true }
                        }
                    },
                    onDoneClick = {
                        navController.navigate(AppRoute.HOME) {
                            popUpTo(AppRoute.HOME) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppRoute.HOME) {
                HomeRoute(
                    onCashInClick = { navController.navigate(AppRoute.CASH_IN_OPTIONS) },
                    onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                )
            }

            composable(AppRoute.LOAN) {
                LoanScreen(
                    viewModel = loanViewModel,
                    onNavigateToApply = { navController.navigate(AppRoute.LOAN_APPLY) },
                    onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                )
            }
            composable(AppRoute.LOAN_APPLY) {
                LoanApplyScreen(
                    viewModel = loanViewModel,
                    onBack = { navController.popBackStack() },
                    onSuccess = { navController.navigate(AppRoute.LOAN_SUCCESS) }
                )
            }
            composable(AppRoute.LOAN_SUCCESS) {
                LoanSuccessScreen(
                    viewModel = loanViewModel,
                    onDone = { navController.navigate(AppRoute.LOAN_HISTORY) }
                )
            }
            composable(AppRoute.LOAN_HISTORY) {
                LoanHistoryScreen(
                    viewModel = loanViewModel,
                    onBack = {
                        navController.navigate(AppRoute.LOAN) {
                            popUpTo(AppRoute.LOAN) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppRoute.SHOP) {
                ShopScreen(
                    onSearchClick = { navController.navigate(AppRoute.SHOP_SEARCH) },
                    onFilterClick = { navController.navigate(AppRoute.FILTER) },
                    onProductClick = { productId ->
                        navController.navigate(AppRoute.productDetail(productId))
                    },
                    onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                )
            }

            composable(AppRoute.SHOP_SEARCH) {
                ShopSearchScreen(
                    onBackClick = { navController.popBackStack() },
                    onSearchClick = { /* Opcionalmente manejar la búsqueda */ }
                )
            }

            composable(
                route = AppRoute.PRODUCT_DETAIL_WITH_ARG,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId").orEmpty()
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoute.FILTER) {
                FilterScreen(
                    onBackClick = { navController.popBackStack() },
                    onApplyClick = { navController.popBackStack() }
                )
            }

            composable(AppRoute.HISTORY) {
                HistoryRoute(
                    onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) },
                    onTransactionClick = { transactionId ->
                        navController.navigate(AppRoute.transactionDetail(transactionId))
                    }
                )
            }

            composable(
                route = AppRoute.TRANSACTION_DETAIL_WITH_ARG,
                arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getString("transactionId").orEmpty()
                TransactionDetailRoute(
                    transactionId = transactionId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppRoute.NOTIFICATIONS) {
                NotificationScreen(onBackClick = { navController.popBackStack() })
            }

            composable(AppRoute.CASH_IN_OPTIONS) {
                CashInOptionsScreen(
                    onBackClick = { navController.popBackStack() },
                    onOnlineBankingClick = { navController.navigate(AppRoute.ONLINE_CASH_IN_OPTIONS) },
                    onOverTheCounterClick = { navController.navigate(AppRoute.OVER_THE_COUNTER_PARTNERS) }
                )
            }

            composable(AppRoute.ONLINE_CASH_IN_OPTIONS) {
                OnlineCashInOptionsScreen(
                    onBackClick = { navController.popBackStack() },
                    onOptionClick = { source ->
                        cashInViewModel.selectedSource = source
                        navController.navigate(AppRoute.CASH_IN_AMOUNT)
                    }
                )
            }

            composable(AppRoute.OVER_THE_COUNTER_PARTNERS) {
                OverTheCounterPartnersScreen(
                    onBackClick = { navController.popBackStack() },
                    onPartnerClick = { source ->
                        cashInViewModel.selectedSource = source
                        navController.navigate(AppRoute.CASH_IN_AMOUNT)
                    }
                )
            }

            composable(AppRoute.CASH_IN_AMOUNT) {
                CashInAmountScreen(
                    sourceName = cashInViewModel.selectedSource,
                    onBackClick = { navController.popBackStack() },
                    onNextClick = { navController.navigate(AppRoute.CASH_IN_SUCCESS) }
                )
            }

            composable(AppRoute.CASH_IN_SUCCESS) {
                CashInSuccessScreen(
                    sourceName = cashInViewModel.selectedSource,
                    onDoneClick = {
                        navController.navigate(AppRoute.HOME) {
                            popUpTo(AppRoute.HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppRoute.MANAGE) {
                ProfilePage(
                    onOptionClick = { option ->
                        when (option) {
                            "Account details" -> navController.navigate(AppRoute.PROFILE_DETAIL)
                            "Credit score" -> navController.navigate(AppRoute.CREDIT_SCORE)
                        }
                    },
                    onEditClick = { navController.navigate(AppRoute.PROFILE_DETAIL) },
                    onLogOutClick = {
                        sessionManager.clearSession()
                        navController.navigate(AppRoute.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                )
            }

            composable(AppRoute.PROFILE_DETAIL) {
                ProfileDetailPage(
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = { navController.navigate(AppRoute.MANAGE_DONE) }
                )
            }

            composable(AppRoute.MANAGE_DONE) {
                ManageDonePage(
                    onExitClick = {
                        navController.navigate(AppRoute.MANAGE) {
                            popUpTo(AppRoute.MANAGE) { inclusive = true }
                        }
                    },
                    onDoneClick = {
                        navController.navigate(AppRoute.MANAGE) {
                            popUpTo(AppRoute.MANAGE) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppRoute.CREDIT_SCORE) {
                CreditScorePage(
                    onBackClick = { navController.popBackStack() },
                    onOptionClick = { option ->
                        if (option == "Account details") {
                            navController.navigate(AppRoute.PROFILE_DETAIL)
                        }
                    }
                )
            }
        }
    }
}
