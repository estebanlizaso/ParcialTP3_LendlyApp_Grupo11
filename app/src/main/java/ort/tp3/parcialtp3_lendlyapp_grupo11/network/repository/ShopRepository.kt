package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import kotlinx.coroutines.flow.Flow
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.ProductDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.BrandEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.CategoryEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.ProductEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.mapper.toEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopRepository @Inject constructor(
    private val productDao: ProductDao
) {
    // Flow para observar todos los productos (ShopScreen)
    val products: Flow<List<ProductEntity>> = productDao.getAllProducts()
    val brands: Flow<List<BrandEntity>> = productDao.getAllBrands()
    val categories: Flow<List<CategoryEntity>> = productDao.getAllCategories()
    val bestSellers: Flow<List<ProductEntity>> = productDao.getBestSellerProducts()
    val recommended: Flow<List<ProductEntity>> = productDao.getRecommendedProducts()

    // Para el detalle del producto
    suspend fun getProductById(productId: String): ProductEntity? {
        return productDao.getProductById(productId)
    }
}
