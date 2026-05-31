package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.ApiClient
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi

class HomeRepository(
    private val api: LendlyApi = ApiClient.api
) {
    suspend fun getUser(id: Int = 1): UserResponse = api.getUser(id)

    suspend fun getLoans(): LoansResponse = api.getLoans()

    suspend fun getProducts(): ProductsResponse = api.getProducts()

    suspend fun getTransactions(): TransactionsResponse = api.getTransactions()
}
