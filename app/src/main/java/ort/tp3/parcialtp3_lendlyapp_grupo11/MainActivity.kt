package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        startDestination = AppRoute.HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
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

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    // Definimos el estado para los inputs
    var phoneNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+65") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ejemplo de Inputs alineados (como en tu imagen)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            AppLabel(
                label = "Phone Number",
                value = countryCode,
                onValueChange = { countryCode = it },
                modifier = Modifier.width(80.dp),
                placeholder = "+54"
            )
            AppLabel(
                label = "", // Sin label para que alinee con el anterior
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.weight(1f),
                placeholder = "11 2345 6789",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón que usa los datos ingresados
        AppButton(
            text = "Continuar",
            onClick = { 
                println("Iniciando sesión con: $countryCode $phoneNumber")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        LoginScreen()
    }
}
