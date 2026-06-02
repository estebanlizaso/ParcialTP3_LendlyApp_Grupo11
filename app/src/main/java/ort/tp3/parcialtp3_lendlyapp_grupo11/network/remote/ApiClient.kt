package ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://6d710e79-f4ca-4651-909f-7dd13bd29968.mock.pstmn.io/"

    val api: LendlyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LendlyApi::class.java)
}
