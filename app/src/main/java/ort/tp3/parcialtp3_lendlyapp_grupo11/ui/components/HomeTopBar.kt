package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenDark2
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundTextIcon(text = "U")
            Spacer(modifier = Modifier.weight(1f))
            RoundTextIcon(text = "!")
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Lendly logo",
            modifier = Modifier.size(width = 54.dp, height = 28.dp)
        )
    }
}

@Composable
private fun RoundTextIcon(text: String) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(GreenDark2),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = BlackFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold
        )
    }
}
