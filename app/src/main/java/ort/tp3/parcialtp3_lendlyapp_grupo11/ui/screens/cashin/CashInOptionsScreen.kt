package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OptionListItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ScreenTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun CashInOptionsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onOnlineBankingClick: () -> Unit = {},
    onOverTheCounterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenLight3)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        ScreenTopBar(
            title = "Cash-In",
            showInfo = true,
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Cash-In Options",
            color = BlackFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(24.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = androidx.compose.ui.graphics.Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                OptionListItem(
                    title = "Online Banking",
                    subtitle = "Pay via other banks or e-wallet",
                    iconText = "B",
                    onClick = onOnlineBankingClick
                )
                OptionListItem(
                    title = "Over-the-counter",
                    subtitle = "Pay in cash",
                    iconText = "P",
                    onClick = onOverTheCounterClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CashInOptionsScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        CashInOptionsScreen()
    }
}
