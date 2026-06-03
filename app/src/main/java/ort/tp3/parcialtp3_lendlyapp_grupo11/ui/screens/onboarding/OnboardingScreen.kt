package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ButtonType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.OnboardingText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding.PagerIndicator
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.White

@Composable
fun OnboardingScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
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
                    onClick = onLoginClick,
                    type = ButtonType.OUTLINED,
                    borderColor = White,
                    textColor = White,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                AppButton(
                    text = stringResource(R.string.onboarding3_register_button),
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
