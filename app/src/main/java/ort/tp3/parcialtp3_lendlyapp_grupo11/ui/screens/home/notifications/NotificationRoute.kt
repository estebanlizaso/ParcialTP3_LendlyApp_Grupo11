package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NotificationRoute(
    onBackClick: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NotificationScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onCalendarClick = viewModel::onCalendarClick,
        onDismissCalendar = viewModel::onDismissCalendar,
        onPreviousMonthClick = viewModel::onPreviousMonthClick,
        onNextMonthClick = viewModel::onNextMonthClick,
        onDateClick = viewModel::onDateSelected,
        onNotificationClick = viewModel::onNotificationSelected,
        onDismissDayDialog = viewModel::onDismissDayDialog
    )
}
