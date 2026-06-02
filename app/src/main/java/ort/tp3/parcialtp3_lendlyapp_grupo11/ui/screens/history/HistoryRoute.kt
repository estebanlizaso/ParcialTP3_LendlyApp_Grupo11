package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun HistoryRoute(
    onNotificationClick: () -> Unit,
    onTransactionClick: (String) -> Unit,
    uiState: HistoryUiState = remember { sampleHistoryUiState() }
) {
    HistoryScreen(
        uiState = uiState,
        onNotificationClick = onNotificationClick,
        onTransactionClick = onTransactionClick
    )
}
