package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconType

data class TransactionDetailUiState(
    val isLoading: Boolean = true,
    val detail: TransactionDetailUi? = null,
    val error: String? = null
)

data class TransactionDetailUi(
    val iconType: TransactionIconType,
    val title: String,
    val amount: String,
    val destination: String,
    val category: String,
    val fee: String,
    val dateTime: String,
    val transactionNumber: String
)
