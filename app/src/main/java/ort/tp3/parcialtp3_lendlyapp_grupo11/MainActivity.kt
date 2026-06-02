package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.CreatePasswordPage
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.SplashScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.HistoryRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history.TransactionDetailRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.HomeRoute
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.NotificationScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*

@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(DarkGreen),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, color = White, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            AppButton(text = "Go Back", onClick = onBack)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CreatePasswordPage(
                        modifier = Modifier.padding(innerPadding),
                        onNextClick = {
                            println("¡REGISTRO EXITOSO! La API devolvió el token y deberíamos ir a la DonePage.")
                        },
                        onBackClick = { }
                    )
                }
                val navController = rememberNavController()
                val bottomNavItems = lendlyBottomNavItems()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val selectedNavIndex = selectedBottomNavIndex(currentRoute)
                var cashInSource by remember { mutableStateOf("BPI") }

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
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // SPLASH
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onTimeout = {
                                    val isUserLoggedIn = true // Cambiar a true para probar navegación a Home
                                    if (isUserLoggedIn) {
                                        navController.navigate(AppRoute.HOME) {
                                            popUpTo(Screen.Splash.route) { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate("onboarding_flow") {
                                            popUpTo(Screen.Splash.route) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }

                        // ONBOARDING FLOW
                        composable("onboarding_flow") {
                            val pagerState = rememberPagerState(pageCount = { 3 })
                            val scope = rememberCoroutineScope()
                            val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(DarkGreen),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(Modifier.height(systemBarsPadding.calculateTopPadding() + 32.dp))
                                Logo(modifier = Modifier.width(117.dp))
                                Spacer(Modifier.height(32.dp))

                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    id = when (page) {
                                                        0 -> R.drawable.onboarding_quick_loans
                                                        1 -> R.drawable.onboarding_loan_product
                                                        else -> R.drawable.onboarding_pay_easily
                                                    }
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxWidth(),
                                                contentScale = ContentScale.FillWidth,
                                                alignment = Alignment.CenterEnd
                                            )
                                        }
                                        Spacer(Modifier.height(32.dp))
                                        OnboardingText(
                                            title = stringResource(
                                                id = when (page) {
                                                    0 -> R.string.onboarding1_title
                                                    1 -> R.string.onboarding2_title
                                                    else -> R.string.onboarding3_title
                                                }
                                            ),
                                            subtitle = when (page) {
                                                0 -> stringResource(R.string.onboarding1_subtitle)
                                                1 -> stringResource(R.string.onboarding2_subtitle)
                                                else -> ""
                                            }
                                        )
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                        .padding(bottom = systemBarsPadding.calculateBottomPadding() + 32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    PagerIndicator(
                                        count = 3,
                                        selectedIndex = pagerState.currentPage,
                                        selectedColor = Green,
                                        unselectedColor = GreenLight
                                    )
                                    Spacer(Modifier.height(32.dp))
                                    if (pagerState.currentPage < 2) {
                                        AppButton(
                                            text = stringResource(
                                                if (pagerState.currentPage == 0) R.string.onboarding1_get_started_button
                                                else R.string.onboarding2_get_started_button
                                            ),
                                            onClick = {
                                                scope.launch { pagerState.animateScrollToPage(2) }
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    } else {
                                        AppButton(
                                            text = stringResource(R.string.onboarding3_login_button),
                                            onClick = {
                                                navController.navigate(Screen.Login.route)
                                            },
                                            type = ButtonType.OUTLINED,
                                            borderColor = White,
                                            textColor = White,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(Modifier.height(16.dp))
                                        AppButton(
                                            text = stringResource(R.string.onboarding3_register_button),
                                            onClick = { navController.navigate(Screen.Signup.route) },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }

                        // AUTH PLACEHOLDERS
                        composable(Screen.Login.route) {
                            PlaceholderScreen(title = "Login Screen", onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Signup.route) {
                            PlaceholderScreen(title = "Sign-up Screen", onBack = { navController.popBackStack() })
                        }

                        // APP ROUTES
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
                                        popUpTo(AppRoute.HOME) { inclusive = true }
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
