package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreyDivider

@Composable
fun CloseIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = BlackIcon,
    backgroundColor: Color = GreyDivider
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(color = backgroundColor, shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
            tint = iconTint,
            modifier = Modifier.size(13.dp)
        )
    }
}
