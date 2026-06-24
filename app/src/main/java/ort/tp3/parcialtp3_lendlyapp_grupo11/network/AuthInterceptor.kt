package ort.tp3.parcialtp3_lendlyapp_grupo11.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val API_KEY = "123456789"

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        
        requestBuilder.addHeader("x-api-key", API_KEY)

        return chain.proceed(requestBuilder.build())
    }
}
