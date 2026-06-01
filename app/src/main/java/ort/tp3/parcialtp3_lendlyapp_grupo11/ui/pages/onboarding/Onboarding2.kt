package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OnboardingPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme

@Composable
fun Onboarding2(
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit = {},
) {
    OnboardingPage(
        modifier = modifier,
        illustration = {
            Image(
                painter = painterResource(id = R.drawable.onboarding_loan_product),
                contentDescription = R.string.onboarding2_title.toString(),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.CenterEnd
            )
        },
        textContent = {
            OnboardingText(
                title = stringResource(R.string.onboarding2_title),
                subtitle = stringResource(R.string.onboarding2_subtitle)
            )
        },
        footerContent = {
            PagerIndicator(
                count = 3,
                selectedIndex = 1,
                selectedColor = Green,
                unselectedColor = Color(0xFF1B3B1D)
            )

            Spacer(Modifier.height(32.dp))

            AppButton(
                text = stringResource(R.string.onboarding2_get_started_button),
                onClick = onGetStarted,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Preview(showBackground = false)
@Composable
fun Onboarding2Preview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        Onboarding2()
    }
}
