package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

@Composable
fun HomeTopBar(
    avatarUrl: String,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(avatarUrl = avatarUrl)
            Spacer(modifier = Modifier.weight(1f))
            NotificationBellButton(onClick = onNotificationClick)
        }
        
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Lendly logo",
            modifier = Modifier
                .width(58.26.dp)
                .height(20.dp)
        )
    }
}

@Composable
private fun UserAvatar(avatarUrl: String) {
    Image(
        painter = painterResource(id = R.drawable.ic_avatar_placeholder),
        contentDescription = "User avatar",
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun NotificationBellButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_notification_bell),
            contentDescription = "Notifications",
            modifier = Modifier.size(24.dp)
        )
    }
}