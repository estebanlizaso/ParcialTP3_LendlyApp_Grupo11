package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.CreditScorePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ManageDonePage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ProfileDetailPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage.ProfilePage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.FilterScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ProductDetailScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopSearchScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*

@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit = {}) {
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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                val registerViewModel = remember { RegisterViewModel(sessionManager = sessionManager) }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val selectedNavIndex = selectedBottomNavIndex(currentRoute)
                var cashInSource by remember { mutableStateOf("BPI") }

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
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // SPLASH
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onTimeout = {
                                    if (sessionManager.isLoggedIn()) {
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
                                            onClick = { navController.navigate(Screen.Login.route) },
                                            type = ButtonType.OUTLINED,
                                            borderColor = White,
                                            textColor = White,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(Modifier.height(16.dp))
                                        AppButton(
                                            text = stringResource(R.string.onboarding3_register_button),
                                            onClick = { navController.navigate(AppRoute.VERIFY_PHONE_NUMBER) },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }

                        // AUTH
                        composable(Screen.Login.route) {
                            LoginPage(
                                onLoginSuccess = {
                                    navController.navigate(AppRoute.HOME) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Signup.route) {
                            ProfileDetailFormPage(
                                viewModel = registerViewModel,
                                onBackClick = { navController.popBackStack() },
                                onNextClick = { navController.navigate(AppRoute.SIGNATURE) }
                            )
                        }

                        // REGISTRATION STEPS
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
                                onNextClick = { navController.navigate(Screen.Signup.route) }
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

                        // MAIN APP FLOW
                        composable(AppRoute.HOME) {
                            HomeRoute(
                                onCashInClick = { navController.navigate(AppRoute.CASH_IN_OPTIONS) },
                                onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                            )
                        }
                        
                        composable(AppRoute.LOAN) {
                            PlaceholderScreen(title = "Loan Screen", onBack = { navController.popBackStack() })
                        }
                        
                        composable(AppRoute.SHOP) {
                            ShopScreen(
                                onSearchClick = { navController.navigate(AppRoute.SHOP_SEARCH) },
                                onFilterClick = { navController.navigate(AppRoute.FILTER) },
                                onProductClick = { productId ->
                                    navController.navigate(AppRoute.productDetail(productId))
                                }
                            )
                        }

                        composable(AppRoute.SHOP_SEARCH) {
                            ShopSearchScreen(
                                onBackClick = { navController.popBackStack() },
                                onSearchClick = { query ->
                                    // Opcionalmente manejar la búsqueda aquí
                                }
                            )
                        }
                        
                        composable(AppRoute.PRODUCT_DETAIL_WITH_ARG) { backStackEntry ->
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

                        // MANAGE FLOW
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
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onNotificationClick = { navController.navigate(AppRoute.NOTIFICATIONS) }
                            )
                        }

                        composable(AppRoute.PROFILE_DETAIL) {
                            ProfileDetailPage(
                                onBackClick = { navController.popBackStack() },
                                onSaveClick = { navController.navigate("manage_done") }
                            )
                        }

                        composable("manage_done") {
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
        }
    }
}
