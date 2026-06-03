package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Neutral98
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun AccountBalanceCard(
    balance: String,
    modifier: Modifier = Modifier,
    onCashInClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Neutral98,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "AVAILABLE BALANCE",
                    color = GrayText,
                    fontSize = 11.sp,
                    letterSpacing = 0.6.sp,
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.size(22.dp))
                Text(
                    text = balance,
                    color = BlackFont,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts
                )
            }

            AppButton(
                text = "Cash In",
                onClick = onCashInClick,
                fillMaxWidth = false,
                height = 42.dp,
                horizontalPadding = 18.dp,
                backgroundColor = Green,
                textColor = Color.Black,
                icon = {
                    Text(
                        text = "+",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    }
}
