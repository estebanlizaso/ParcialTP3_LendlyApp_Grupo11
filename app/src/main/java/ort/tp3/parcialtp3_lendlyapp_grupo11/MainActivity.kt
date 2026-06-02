package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ButtonType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.Screen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan.LoanApplyScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan.LoanHistoryScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan.LoanScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan.LoanSuccessScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.SplashScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.White
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

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
                val navController = rememberNavController()
                val loanViewModel: LoanViewModel = viewModel() // TODO LOAN - Se agrega solo para loan

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.fillMaxSize() // TODO LOAN - Se saca solo para loan
                        //modifier = Modifier.fillMaxSize().padding(innerPadding) // TODO LOAN - Se agrega solo para loan
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onTimeout = {
                                    // Placeholder para lógica de login persistente
                                    val isUserLoggedIn = false // Cambiar a true para probar navegación a Home //TODO LOAN - Se saca solo para loan
                                    //val isUserLoggedIn = true // TODO LOAN - Cambiado para probar navegación a Loan sin pasar por home

                                    if (isUserLoggedIn) {
                                        //navController.navigate(Screen.Home.route) { //TODO LOAN - Se saca solo para loan
                                        navController.navigate(Screen.Loan.route) { //TODO LOAN - Se agrega solo para loan
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

                        composable("onboarding_flow") {
                            val pagerState = rememberPagerState(pageCount = { 3 })
                            val scope = rememberCoroutineScope()
                            val systemBarsPadding = WindowInsets.systemBars.asPaddingValues() //TODO LOAN - Se saca solo para loan

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(DarkGreen),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // STATIC LOGO
                                Spacer(Modifier.height(systemBarsPadding.calculateTopPadding() + 32.dp)) //TODO LOAN - Se saca solo para loan
                                //Spacer(Modifier.height(32.dp)) //TODO LOAN - Se agrega solo para loan
                                Logo(modifier = Modifier.width(117.dp))
                                
                                Spacer(Modifier.height(32.dp))

                                // Pager
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // Imagen
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 16.dp),
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

                                        // Texto
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

                                // Indicador + Botones
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                        .padding(bottom = systemBarsPadding.calculateBottomPadding() + 32.dp), // TODO LOAN - Se saca solo para loan
                                        //.padding(bottom = 32.dp), //TODO LOAN - Se agrega solo para loan
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
                                                scope.launch {
                                                    pagerState.animateScrollToPage(2)
                                                }
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
                                            onClick = { 
                                                navController.navigate(Screen.Signup.route)
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        // TODO LOAN - Se agrega solo para loan desde acá
                        }

                        // LOAN SECTION
                        composable(Screen.Loan.route) {
                            LoanScreen(
                                onNavigateToApply = { navController.navigate(Screen.LoanApply.route) }
                            )
                        }
                        composable(Screen.LoanApply.route) {
                            LoanApplyScreen(
                                viewModel = loanViewModel,
                                onBack = { navController.popBackStack() },
                                onSuccess = { navController.navigate(Screen.LoanSuccess.route) }
                            )
                        }
                        composable(Screen.LoanSuccess.route) {
                            LoanSuccessScreen(
                                onDone = { navController.navigate(Screen.LoanHistory.route) }
                            )
                        }
                        composable(Screen.LoanHistory.route) {
                            LoanHistoryScreen(
                                viewModel = loanViewModel,
                                onBack = { navController.popBackStack() }
                            )
                            //TODO LOAN - Se agrega solo para loan hasta acá
                        }

                        // PLACEHOLDERS PARA FUTURAS PANTALLAS
                        composable(Screen.Login.route) {
                            PlaceholderScreen(title = "Login Screen", onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Signup.route) {
                            PlaceholderScreen(title = "Sign-up Screen", onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Home.route) {
                            PlaceholderScreen(title = "Home Screen", onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

