package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    onCashInClick: () -> Unit,
    onNotificationClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onCashInClick = onCashInClick,
        onNotificationClick = onNotificationClick
    )
}
