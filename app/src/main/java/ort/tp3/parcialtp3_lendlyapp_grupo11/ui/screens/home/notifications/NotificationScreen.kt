package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.CalendarIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components.NotificationCalendarCard
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications.components.NotificationDayDialog
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun NotificationScreen(
    uiState: NotificationUiState,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onDismissCalendar: () -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (CalendarDayUi) -> Unit,
    onNotificationClick: (NotificationDayItemUi) -> Unit,
    onDismissDayDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    val todayItems = remember(uiState.notifications, uiState.todayDateKey) {
        uiState.notifications.filter { it.dateKey == uiState.todayDateKey }
    }
    val otherItems = remember(uiState.notifications, uiState.todayDateKey) {
        uiState.notifications.filterNot { it.dateKey == uiState.todayDateKey }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))
            AppTopBar(
                onLeftClick = onBackClick,
                modifier = Modifier.background(Color.White),
                rightIcon = { CalendarIcon(onClick = onCalendarClick) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Notification",
                    color = BlackFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts
                )

                Spacer(modifier = Modifier.height(28.dp))
                NotificationStatusMessage(uiState = uiState)

                if (!uiState.isLoading && uiState.error == null) {
                    if (uiState.notifications.isEmpty()) {
                        EmptyNotificationsMessage()
                    } else {
                        NotificationGroup(
                            title = "Today",
                            items = todayItems,
                            onNotificationClick = onNotificationClick
                        )

                        Spacer(modifier = Modifier.height(26.dp))
                        NotificationGroup(
                            title = "Activity",
                            items = otherItems,
                            onNotificationClick = onNotificationClick
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (uiState.isCalendarVisible) {
            Dialog(
                onDismissRequest = onDismissCalendar,
                properties = DialogProperties(
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                )
            ) {
                NotificationCalendarCard(
                    month = uiState.visibleMonth,
                    selectedDate = uiState.selectedDate,
                    markedDateKeys = uiState.markedDateKeys,
                    onPreviousMonthClick = onPreviousMonthClick,
                    onNextMonthClick = onNextMonthClick,
                    onDateClick = onDateClick,
                    onOkClick = onDismissCalendar,
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                )
            }
        }

        uiState.selectedDate?.let { selectedDate ->
            NotificationDayDialog(
                selectedDate = selectedDate,
                items = uiState.selectedDayItems,
                onDismiss = onDismissDayDialog
            )
        }
    }
}

@Composable
private fun NotificationStatusMessage(uiState: NotificationUiState) {
    val message = when {
        uiState.isLoading -> "Loading notifications..."
        uiState.error != null -> uiState.error
        else -> null
    }

    message?.let {
        Text(
            text = it,
            color = GrayText,
            fontSize = 13.sp,
            fontFamily = interFonts,
            modifier = Modifier.padding(bottom = 18.dp)
        )
    }
}

@Composable
private fun EmptyNotificationsMessage() {
    Text(
        text = "No notifications yet",
        color = GrayText,
        fontSize = 13.sp,
        fontFamily = interFonts
    )
}

@Composable
private fun NotificationGroup(
    title: String,
    items: List<NotificationDayItemUi>,
    onNotificationClick: (NotificationDayItemUi) -> Unit
) {
    if (items.isEmpty()) return

    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        NotificationSection(title = title)
        items.forEach { item ->
            NotificationItem(
                item = item,
                unread = item.type == NotificationDayItemType.LOAN_DUE_DATE,
                onClick = { onNotificationClick(item) }
            )
        }
    }
}

@Composable
private fun NotificationSection(title: String) {
    Text(
        text = title,
        color = GrayText,
        fontSize = 13.sp,
        fontFamily = interFonts,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun NotificationItem(
    item: NotificationDayItemUi,
    unread: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(if (unread) Green else Color(0xFFE9E4E4))
        )

        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title,
                    color = BlackFont,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFonts,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.dateLabel,
                    color = GrayText,
                    fontSize = 11.sp,
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.descriptionText(),
                color = GrayText,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontFamily = interFonts
            )
        }
    }
}

private fun NotificationDayItemUi.descriptionText(): String {
    return when (type) {
        NotificationDayItemType.LOAN_DUE_DATE -> "You have a loan payment scheduled with $subtitle. Tap to review this day."
        NotificationDayItemType.ADDED_BALANCE,
        NotificationDayItemType.PAYMENT -> "${subtitle.ifBlank { "Transaction" }}${amount?.let { " - $it" }.orEmpty()}"
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        NotificationScreen(
            uiState = NotificationUiState(
                isLoading = false,
                notifications = listOf(
                    NotificationDayItemUi(
                        id = "1",
                        dateKey = NotificationDateUtils.todayDateKey(),
                        dateLabel = "Mar 8",
                        timeLabel = "9:00 AM",
                        title = "Loan payment due",
                        subtitle = "Phone - Apple",
                        amount = "1,200.00 PHP",
                        type = NotificationDayItemType.LOAN_DUE_DATE
                    )
                ),
                markedDateKeys = setOf(NotificationDateUtils.todayDateKey())
            ),
            onBackClick = {},
            onCalendarClick = {},
            onDismissCalendar = {},
            onPreviousMonthClick = {},
            onNextMonthClick = {},
            onDateClick = {},
            onNotificationClick = {},
            onDismissDayDialog = {}
        )
    }
}
