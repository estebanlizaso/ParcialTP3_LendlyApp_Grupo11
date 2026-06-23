package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onCashInClick: () -> Unit,
    onNotificationClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onCashInClick = onCashInClick,
        onNotificationClick = onNotificationClick
    )
}
