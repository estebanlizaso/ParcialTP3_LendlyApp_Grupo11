package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.Screen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding1
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding2
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.Onboarding3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding.SplashScreen
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
                                    navController.navigate(Screen.Onboarding1.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Onboarding1.route) {
                            Onboarding1(
                                onGetStarted = {
                                    navController.navigate(Screen.Onboarding2.route)
                                }
                            )
                        }
                        composable(Screen.Onboarding2.route) {
                            Onboarding2(
                                onGetStarted = {
                                    navController.navigate(Screen.Onboarding3.route)
                                }
                            )
                        }
                        composable(Screen.Onboarding3.route) {
                            Onboarding3(
                                onGetStarted = {
                                    // TODO: Navegar a Login o Home
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

