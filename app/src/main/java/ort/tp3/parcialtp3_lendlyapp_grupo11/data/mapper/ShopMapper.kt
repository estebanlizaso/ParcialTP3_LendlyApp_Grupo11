package ort.tp3.parcialtp3_lendlyapp_grupo11.data.mapper

import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.BrandEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.CategoryEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.ProductEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductBrandDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductCategoryDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto

fun ProductDto.toEntity(isRecommended: Boolean = false, isBestSeller: Boolean = false): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        brand = brand,
        category = category,
        price = price,
        currency = currency,
        image = image,
        monthlyInstallment = monthlyInstallment,
        installmentMonths = installmentMonths,
        interestRate = interestRate,
        isFeatured = isFeatured,
        isAvailable = isAvailable,
        rating = rating,
        reviewCount = reviewCount,
        description = description,
        isRecommended = isRecommended,
        isBestSeller = isBestSeller
    )
}

fun ProductBrandDto.toEntity(): BrandEntity {
    return BrandEntity(
        id = id,
        name = name,
        logo = logo
    )
}

fun ProductCategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        icon = icon,
        productCount = productCount
    )
}

fun ProductEntity.toDto(): ProductDto {
    return ProductDto(
        id = id,
        name = name,
        brand = brand,
        category = category,
        price = price,
        currency = currency,
        image = image,
        monthlyInstallment = monthlyInstallment,
        installmentMonths = installmentMonths,
        interestRate = interestRate,
        isFeatured = isFeatured,
        isAvailable = isAvailable,
        rating = rating,
        reviewCount = reviewCount,
        description = description
    )
}

fun BrandEntity.toDto(): ProductBrandDto {
    return ProductBrandDto(
        id = id,
        name = name,
        logo = logo
    )
}
