package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val uiState by viewModel.uiState.collectAsState()

    TransactionDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}
