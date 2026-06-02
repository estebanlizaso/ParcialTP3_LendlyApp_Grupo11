package ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://6d710e79-f4ca-4651-909f-7dd13bd29968.mock.pstmn.io/"

    // Creamos el interceptor espía
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Nos muestra el body entero de la respuesta
    }

    // Se lo enchufamos a OkHttp
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val api: LendlyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client) // <-- Agregamos el cliente acá
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LendlyApi::class.java)
}