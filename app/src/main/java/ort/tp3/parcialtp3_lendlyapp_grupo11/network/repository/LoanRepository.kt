package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyRequest
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.ApiClient
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi

class LoanRepository(
    private val api: LendlyApi = ApiClient.api
) {
    suspend fun getLoans(): Result<LoansResponse> {
        return try {
            val response = api.getLoans()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun applyLoan(request: LoanApplyRequest): Result<LoanApplyResponse> {
        return try {
            val response = api.applyLoan(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
