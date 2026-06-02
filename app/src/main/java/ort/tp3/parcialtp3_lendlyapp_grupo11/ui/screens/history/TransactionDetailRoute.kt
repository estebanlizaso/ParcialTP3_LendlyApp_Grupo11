package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TransactionDetailRoute(
    transactionId: String,
    onBackClick: () -> Unit,
    viewModel: TransactionDetailViewModel = viewModel()
) {
    LaunchedEffect(transactionId) {
        viewModel.loadTransaction(transactionId)
    }

    TransactionDetailScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick
    )
}
