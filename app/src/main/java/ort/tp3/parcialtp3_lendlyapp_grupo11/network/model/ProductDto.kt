package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

data class ProductsResponse(
    val success: Boolean,
    val pagination: PaginationDto,
    val featured: List<ProductDto>,
    val categories: List<ProductCategoryDto>,
    val brands: List<ProductBrandDto>,
    val products: List<ProductDto>
)

data class PaginationDto(
    val page: Int,
    val limit: Int,
    val total: Int,
    val hasNextPage: Boolean
)

data class ProductDto(
    val id: String,
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
    val description: String?
)

data class ProductCategoryDto(
    val id: String,
    val name: String,
    val icon: String,
    val productCount: Int
)

data class ProductBrandDto(
    val id: String,
    val name: String,
    val logo: String
)
