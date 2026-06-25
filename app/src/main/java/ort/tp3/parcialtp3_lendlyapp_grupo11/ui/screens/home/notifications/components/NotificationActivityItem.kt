package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconBadge
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.NotificationDayItemType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.NotificationDayItemUi
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun NotificationActivityItem(
    item: NotificationDayItemUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransactionIconBadge(
            type = item.type.toTransactionIconType(),
            badgeSize = 40.dp,
            iconSize = 18.dp,
            highlighted = item.type == NotificationDayItemType.ADDED_BALANCE
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.type.label,
                color = GrayText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = item.title,
                color = BlackFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.subtitle,
                color = GrayText,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                fontFamily = interFonts
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = item.timeLabel,
                color = GrayText,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts,
                textAlign = TextAlign.End
            )
            item.amount?.let { amount ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = amount,
                    color = BlackFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private val NotificationDayItemType.label: String
    get() = when (this) {
        NotificationDayItemType.ADDED_BALANCE -> "Transaction"
        NotificationDayItemType.PAYMENT -> "Transaction"
        NotificationDayItemType.LOAN_DUE_DATE -> "Loan due date"
    }

private fun NotificationDayItemType.toTransactionIconType(): TransactionIconType {
    return when (this) {
        NotificationDayItemType.ADDED_BALANCE -> TransactionIconType.ADD
        NotificationDayItemType.PAYMENT,
        NotificationDayItemType.LOAN_DUE_DATE -> TransactionIconType.PAYMENT
    }
}
