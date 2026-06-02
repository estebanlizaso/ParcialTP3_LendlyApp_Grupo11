package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String = "See All",
    onActionClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = BlackFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold
        )

        Text(
            text = "$actionText  >",
            color = BlackFont,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold,
            modifier = Modifier.clickable { onActionClick() }
        )
    }
}
