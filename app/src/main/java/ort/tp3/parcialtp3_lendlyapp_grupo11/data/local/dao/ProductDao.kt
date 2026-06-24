package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.BrandEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.CategoryEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): ProductEntity?

    @Query("SELECT * FROM products WHERE isRecommended = 1")
    fun getRecommendedProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE isBestSeller = 1")
    fun getBestSellerProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM brands")
    fun getAllBrands(): Flow<List<BrandEntity>>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrands(brands: List<BrandEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM brands")
    suspend fun deleteAllBrands()

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}
