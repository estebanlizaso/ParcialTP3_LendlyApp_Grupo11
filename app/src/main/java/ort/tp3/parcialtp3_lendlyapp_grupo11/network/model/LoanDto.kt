package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

data class LoansResponse(
    val success: Boolean,
    val loans: List<LoanDto>,
    val summary: LoansSummaryDto
)

data class LoanDto(
    val id: String,
    val lender: String,
    val lenderLogo: String,
    val amount: Double,
    val amountDue: Double,
    val installmentAmount: Double,
    val installmentPlan: String,
    val interestRate: Double,
    val purpose: String,
    val status: String,
    val nextPaymentDate: String?,
    val nextPaymentLabel: String?,
    val startDate: String,
    val endDate: String,
    val paidInstallments: Int,
    val totalInstallments: Int,
    val transactionNumber: String
)

data class LoansSummaryDto(
    val totalActive: Int,
    val totalPaid: Int,
    val totalAmountDue: Double
)
