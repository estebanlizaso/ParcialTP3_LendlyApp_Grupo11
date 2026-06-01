package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier,
    illustration: @Composable () -> Unit,
    textContent: @Composable () -> Unit,
    footerContent: @Composable () -> Unit
) {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkGreen),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo Section (with Top Safe Area)
        Spacer(Modifier.height(systemBarsPadding.calculateTopPadding() + 32.dp))
        Logo(modifier = Modifier.width(117.dp))
        
        Spacer(Modifier.height(32.dp))

        // Illustration Section (Centered with left padding)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            illustration()
        }

        Spacer(Modifier.height(32.dp))

        // Content Section (Title, Subtitle, Pager, Button - with Safe Area)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
                .padding(bottom = systemBarsPadding.calculateBottomPadding() + 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Text Group
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                textContent()
            }

            // Footer (Pager + Button)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                footerContent()
            }
        }
    }
}
