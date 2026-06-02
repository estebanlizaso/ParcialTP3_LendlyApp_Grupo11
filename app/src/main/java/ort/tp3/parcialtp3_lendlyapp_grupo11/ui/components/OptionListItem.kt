package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun OptionListItem(
    title: String,
    subtitle: String = "",
    iconText: String,
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color = DarkGreen,
    iconTextColor: Color = BlackFont,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = iconText,
                color = iconTextColor,
                fontSize = if (iconText.length > 2) 9.sp else 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = BlackFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    color = GrayColor,
                    fontSize = 11.sp,
                    fontFamily = interFonts
                )
            }
        }

        Text(
            text = ">",
            color = Color(0xFF40523A),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
