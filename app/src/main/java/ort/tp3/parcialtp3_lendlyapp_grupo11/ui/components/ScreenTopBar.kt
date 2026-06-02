package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun ScreenTopBar(
    title: String,
    modifier: Modifier = Modifier,
    showInfo: Boolean = false,
    onBackClick: () -> Unit = {},
    onInfoClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "<",
                color = BlackFont,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.weight(1f))

            if (showInfo) {
                Text(
                    text = "i",
                    color = BlackFont,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interSemiBold,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onInfoClick() }
                )
            } else {
                Spacer(modifier = Modifier.size(32.dp))
            }
        }

        Text(
            text = title,
            color = BlackFont,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold
        )
    }
}
