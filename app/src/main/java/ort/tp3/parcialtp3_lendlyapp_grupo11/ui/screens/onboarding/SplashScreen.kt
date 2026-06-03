package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.SplashScreenGreen

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000) // 2 seconds delay
        onTimeout()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashScreenGreen),
        contentAlignment = Alignment.Center
    ) {
        Logo(
            modifier = Modifier.size(242.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        SplashScreen()
    }
}