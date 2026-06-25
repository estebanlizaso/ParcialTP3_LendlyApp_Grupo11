package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryRoute(
    onNotificationClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    HistoryScreen(
        uiState = viewModel.uiState,
        onNotificationClick = onNotificationClick,
        onTransactionClick = onTransactionClick
    )
}
