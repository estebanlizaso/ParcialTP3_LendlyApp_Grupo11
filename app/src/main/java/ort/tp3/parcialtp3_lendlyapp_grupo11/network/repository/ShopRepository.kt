package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopRepository @Inject constructor(
    private val api: LendlyApi
) {
    private var cachedResponse: ProductsResponse? = null

    suspend fun getProducts(): ProductsResponse {
        if (cachedResponse == null) {
            cachedResponse = api.getProducts()
        }
        return cachedResponse!!
    }

    fun getProductById(productId: String): ProductDto? {
        return cachedResponse?.products?.find { it.id == productId }
            ?: cachedResponse?.featured?.find { it.id == productId }
    }
}
