package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

data class TransactionsResponse(
    val success: Boolean,
    val pagination: PaginationDto,
    val transactions: List<TransactionDto>
)

data class TransactionDto(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val amount: Double,
    val currency: String,
    val status: String,
    val date: String,
    val loanId: String?,
    val referenceNumber: String
)
