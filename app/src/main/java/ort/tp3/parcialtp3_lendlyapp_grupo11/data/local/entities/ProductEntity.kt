package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val currency: String,
    val image: String,
    val monthlyInstallment: Double,
    val installmentMonths: Int,
    val interestRate: Double,
    val isFeatured: Boolean,
    val isAvailable: Boolean,
    val rating: Double,
    val reviewCount: Int,
    val description: String?,
    val isRecommended: Boolean = false,
    val isBestSeller: Boolean = false,
    val specifications: String? = null,
    val features: String? = null
)

@Entity(tableName = "brands")
data class BrandEntity(
    @PrimaryKey val id: String,
    val name: String,
    val logo: String
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
    val productCount: Int
)
