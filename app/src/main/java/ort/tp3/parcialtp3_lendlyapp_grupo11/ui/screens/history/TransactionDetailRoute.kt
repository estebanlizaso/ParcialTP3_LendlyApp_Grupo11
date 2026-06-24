package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TransactionDetailRoute(
    transactionId: String,
    onBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(transactionId) {
        viewModel.loadTransaction(transactionId)
    }

    TransactionDetailScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick
    )
}
