package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ButtonType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.Screen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding1
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding2
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.SplashScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.SplashScreenGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onTimeout = {
                                    navController.navigate("onboarding_flow") {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }

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
                                // STATIC LOGO
                                Spacer(Modifier.height(systemBarsPadding.calculateTopPadding() + 32.dp))
                                Logo(modifier = Modifier.width(117.dp))
                                
                                Spacer(Modifier.height(32.dp))

                                // ANIMATED CONTENT (Pager)
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        // Illustration
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

                                        // Text Group
                                        OnboardingText(
                                            title = stringResource(
                                                id = when (page) {
                                                    0 -> R.string.onboarding1_title
                                                    1 -> R.string.onboarding2_title
                                                    else -> R.string.onboarding3_title
                                                }
                                            ),
                                            subtitle = if (page < 2) stringResource(
                                                id = when (page) {
                                                    0 -> R.string.onboarding1_subtitle
                                                    else -> R.string.onboarding2_subtitle
                                                }
                                            ) else ""
                                        )
                                    }
                                }

                                // STATIC FOOTER (Indicator + Buttons)
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
                                        unselectedColor = Color(0xFF1B3B1D)
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
                                            onClick = { /* TODO: Log In */ },
                                            type = ButtonType.OUTLINED,
                                            borderColor = Color.White,
                                            textColor = Color.White,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Spacer(Modifier.height(16.dp))

                                        AppButton(
                                            text = stringResource(R.string.onboarding3_register_button),
                                            onClick = { /* TODO: Sign Up */ },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

