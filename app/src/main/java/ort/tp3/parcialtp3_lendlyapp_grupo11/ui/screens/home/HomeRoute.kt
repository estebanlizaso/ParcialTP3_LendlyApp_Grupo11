package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onCashInClick: () -> Unit,
    onNotificationClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    HomeScreen(
        uiState = viewModel.uiState,
        onCashInClick = onCashInClick,
        onNotificationClick = onNotificationClick
    )
}
