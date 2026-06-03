package ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyRequest
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

private const val API_KEY_HEADER = "x-api-key: 123456789"

interface LendlyApi {

    @Headers(API_KEY_HEADER)
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @Headers(API_KEY_HEADER)
    @GET("loans")
    suspend fun getLoans(): LoansResponse

    @Headers(API_KEY_HEADER)
    @POST("loans/apply")
    suspend fun applyLoan(@Body request: LoanApplyRequest): LoanApplyResponse

    @Headers(API_KEY_HEADER)
    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @Headers(API_KEY_HEADER)
    @GET("transactions")
    suspend fun getTransactions(): TransactionsResponse

    @Headers(API_KEY_HEADER)
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @Headers(API_KEY_HEADER)
    @POST("auth/create")
    suspend fun register(@Body request: RegisterRequestDto): RegisterResponseDto
}