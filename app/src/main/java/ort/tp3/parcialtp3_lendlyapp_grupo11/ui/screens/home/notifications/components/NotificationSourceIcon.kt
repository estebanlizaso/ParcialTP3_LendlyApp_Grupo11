package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconBadge
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.NotificationDayItemType

@Composable
fun NotificationSourceIcon(
    logoResId: Int?,
    logoUrl: String?,
    type: NotificationDayItemType,
    contentDescription: String,
    modifier: Modifier = Modifier,
    badgeSize: Dp = 40.dp,
    iconSize: Dp = 18.dp
) {
    when {
        logoResId != null -> {
            Box(
                modifier = modifier
                    .size(badgeSize)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = logoResId),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(badgeSize),
                    contentScale = ContentScale.Crop
                )
            }
        }
        !logoUrl.isNullOrBlank() -> {
            AsyncImage(
                model = logoUrl,
                contentDescription = contentDescription,
                modifier = modifier
                    .size(badgeSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            TransactionIconBadge(
                type = type.toTransactionIconType(),
                badgeSize = badgeSize,
                iconSize = iconSize,
                highlighted = type == NotificationDayItemType.ADDED_BALANCE
            )
        }
    }
}

private fun NotificationDayItemType.toTransactionIconType(): TransactionIconType {
    return when (this) {
        NotificationDayItemType.ADDED_BALANCE -> TransactionIconType.ADD
        NotificationDayItemType.PAYMENT,
        NotificationDayItemType.LOAN_DUE_DATE -> TransactionIconType.PAYMENT
    }
}
