package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight2
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.SplashScreenGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.montserratFonts

@Composable
fun Onboarding1(
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit = {},
) {
    val systemBarsPadding = androidx.compose.foundation.layout.WindowInsets.systemBars.asPaddingValues()

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(DarkGreen)
            .padding(systemBarsPadding)
            .padding(bottom = 32.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = CenterHorizontally) {
            Spacer(Modifier.height(32.dp))
            Logo(modifier = Modifier.width(117.dp))
            
            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.onboarding_quick_loans),
                contentDescription = "Onboarding Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(32.dp))

            OnboardingText(
                title = stringResource(R.string.onboarding1_title),
                subtitle = stringResource(R.string.onboarding1_subtitle)
            )
        }

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            PagerIndicator(
                count = 3,
                selectedIndex = 0,
                selectedColor = Green,
                unselectedColor = Color(0xFF1B3B1D)
            )

            Spacer(Modifier.height(32.dp))

            AppButton(
                text = "Get Started",
                onClick = onGetStarted,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun Onboarding1Preview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        Onboarding1()
    }
}