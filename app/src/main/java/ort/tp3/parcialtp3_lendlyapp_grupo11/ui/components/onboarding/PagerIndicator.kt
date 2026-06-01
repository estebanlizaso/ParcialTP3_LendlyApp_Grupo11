package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight

@Composable
fun PagerIndicator(
    count: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = Green,
    unselectedColor: Color = GreenLight,
    unselectedAlpha: Float = 0.16f
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(count) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == selectedIndex) selectedColor
                        else unselectedColor.copy(alpha = unselectedAlpha)
                    )
            )
        }
    }
}
