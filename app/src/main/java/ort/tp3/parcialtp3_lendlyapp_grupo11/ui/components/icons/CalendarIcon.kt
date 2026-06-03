package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon

@Composable
fun CalendarIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = BlackIcon
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_calendar),
        contentDescription = "Calendar",
        tint = tint,
        modifier = modifier
            .size(19.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    )
}
