package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

data class LoanApplyRequest(
    val amount: Double,
    val installmentPlan: String,
    val purpose: String
)

data class LoanApplyResponse(
    val success: Boolean,
    val message: String,
    val loan: LoanDto?
)
