package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.ApiClient

object ShopRepository {
    private var cachedResponse: ProductsResponse? = null

    suspend fun getProducts(): ProductsResponse {
        if (cachedResponse == null) {
            cachedResponse = ApiClient.api.getProducts()
        }
        return cachedResponse!!
    }

    fun getProductById(productId: String): ProductDto? {
        return cachedResponse?.products?.find { it.id == productId }
            ?: cachedResponse?.featured?.find { it.id == productId }
    }
}
