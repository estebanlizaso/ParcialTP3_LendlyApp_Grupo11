package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayColor
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun LoanListItem(
    brandName: String,
    amount: String,
    feeLabel: String,
    modifier: Modifier = Modifier,
    brandInitial: String = brandName.take(1)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GreenLight3)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = brandInitial,
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Text(
            text = brandName,
            color = BlackFont,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold,
            modifier = Modifier.weight(1f)
        )

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = amount,
                color = BlackFont,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold,
                textAlign = TextAlign.End
            )
            Text(
                text = feeLabel,
                color = GrayColor,
                fontSize = 11.sp,
                fontFamily = interSemiBold,
                textAlign = TextAlign.End
            )
        }
    }
}
