package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AppTopBar(
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit = {}
) {
    val iconColor = Color(0xFF171D1E)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = onInfoClick) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}