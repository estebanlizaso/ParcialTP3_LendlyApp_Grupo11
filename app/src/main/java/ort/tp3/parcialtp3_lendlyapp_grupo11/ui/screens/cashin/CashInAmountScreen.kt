package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Neutral98
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun CashInAmountScreen(
    sourceName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Neutral98)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AppTopBar(
            onLeftClick = onBackClick,
            leftIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = BlackIcon,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Balance: ₱0.00",
            color = GrayText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "₱2,500.00",
            color = BlackFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))
        HorizontalDivider(color = Color(0xFFE5E0E0))

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "$sourceName's max limit is ₱10,000.00 per day",
            color = GrayText,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            text = "Next",
            modifier = Modifier.fillMaxWidth(),
            onClick = onNextClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CashInAmountScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        CashInAmountScreen(sourceName = "BPI")
    }
}
