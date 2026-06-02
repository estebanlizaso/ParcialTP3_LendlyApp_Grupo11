package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.CreatePasswordPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.DonePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.FaceRecognitionPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.IDVerificationPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.LoginPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.ProfileDetailFormPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.SMSVerification
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.SignaturePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.VerifiedPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.VerifyPhoneNumber
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppBottomNavigationBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppLabel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.AppRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.lendlyBottomNavItems
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.selectedBottomNavIndex
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInAmountScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInOptionsScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInSuccessScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.OnlineCashInOptionsScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.OverTheCounterPartnersScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.HistoryRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.TransactionDetailRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.HomeRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.NotificationScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                val navController = rememberNavController()
                val bottomNavItems = lendlyBottomNavItems()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val selectedNavIndex = selectedBottomNavIndex(currentRoute)
                var cashInSource by remember { mutableStateOf("BPI") }
                val navigateToHome = {
                    navController.navigate(AppRoute.HOME) {
                        popUpTo(AppRoute.HOME)
                        launchSingleTop = true
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (selectedNavIndex >= 0) {
                            Column {
                                HorizontalDivider(color = Color(0xFFE6E6E6))
                                AppBottomNavigationBar(
                                    items = bottomNavItems,
                                    selectedIndex = selectedNavIndex,
                                    onItemClick = { index ->
                                        val route = bottomNavItems.getOrNull(index)?.route
                                        if (route != null && route != currentRoute) {
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
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.LOGIN,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(AppRoute.LOGIN) {
                            LoginPage(
                                onLoginSuccess = { navController.navigate(AppRoute.VERIFY_PHONE_NUMBER) }
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

                        composable(AppRoute.FACE_RECOGNITION) {
                            FaceRecognitionPage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.ID_VERIFICATION) }
                            )
                        }

                        composable(AppRoute.ID_VERIFICATION) {
                            IDVerificationPage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.VERIFIED) }
                            )
                        }

                        composable(AppRoute.VERIFIED) {
                            VerifiedPage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.PROFILE_DETAIL_FORM) }
                            )
                        }

                        composable(AppRoute.PROFILE_DETAIL_FORM) {
                            ProfileDetailFormPage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.SIGNATURE) }
                            )
                        }

                        composable(AppRoute.SIGNATURE) {
                            SignaturePage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.CREATE_PASSWORD) }
                            )
                        }

                        composable(AppRoute.CREATE_PASSWORD) {
                            CreatePasswordPage(
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.DONE) }
                            )
                        }

                        composable(AppRoute.DONE) {
                            DonePage(
                                onExitClick = { navController.popBackStack() },
                                onDoneClick = {
                                    navController.navigate(AppRoute.HOME) {
                                        popUpTo(AppRoute.LOGIN) { inclusive = true }
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

                        composable(AppRoute.HISTORY) {
                            HistoryRoute(
                                onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) },
                                onTransactionClick = { transactionId ->
                                    navController.navigate(AppRoute.transactionDetail(transactionId))
                                }
                            )
                        }

                        composable(AppRoute.TRANSACTION_DETAIL_WITH_ARG) { backStackEntry ->
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
                                    cashInSource = source
                                    navController.navigate(AppRoute.CASH_IN_AMOUNT)
                                }
                            )
                        }

                        composable(AppRoute.OVER_THE_COUNTER_PARTNERS) {
                            OverTheCounterPartnersScreen(
                                onBackClick = { navController.popBackStack() },
                                onPartnerClick = { source ->
                                    cashInSource = source
                                    navController.navigate(AppRoute.CASH_IN_AMOUNT)
                                }
                            )
                        }

                        composable(AppRoute.CASH_IN_AMOUNT) {
                            CashInAmountScreen(
                                sourceName = cashInSource,
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.CASH_IN_SUCCESS) }
                            )
                        }

                        composable(AppRoute.CASH_IN_SUCCESS) {
                            CashInSuccessScreen(
                                sourceName = cashInSource,
                                onDoneClick = {
                                    navController.navigate(AppRoute.HOME) {
                                        popUpTo(AppRoute.HOME)
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}