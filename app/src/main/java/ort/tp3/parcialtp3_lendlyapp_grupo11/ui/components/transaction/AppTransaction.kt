package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.AddGreenIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Neutral98
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.montserratFonts

@Composable
fun AppTransaction(
    amount: String,
    currency: String = "PHP",
    statusText: String,
    originText: String,
    typeLabel: String,
    icon: @Composable () -> Unit = { AddGreenIcon() }
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Neutral98)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()

        Spacer(Modifier.height(16.dp))

        Text(
            text = statusText,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = GrayText,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = "$amount $currency",
            style = TextStyle(
                fontFamily = montserratFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = 0.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        )

        Text(
            text = originText,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = GrayText,
                textAlign = TextAlign.Center
            )
        )

        Spacer(Modifier.height(16.dp))

        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, GrayText)
        ) {
            Text(
                text = typeLabel,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                    color = GrayText,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
