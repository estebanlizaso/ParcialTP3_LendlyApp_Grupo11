package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

@Composable
fun AppTopBar(
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit = {}
) {
    val iconColor = Color(0xFF171D1E)

    // variables para anular fondo gris
    val backInteractionSource = remember { MutableInteractionSource() }
    val infoInteractionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = backInteractionSource,
                    indication = null, // sacar fondo gris
                    onClick = onBackClick
                )
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_info),
            contentDescription = "Info",
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = infoInteractionSource,
                    indication = null,
                    onClick = onInfoClick
                )
        )
    }
}