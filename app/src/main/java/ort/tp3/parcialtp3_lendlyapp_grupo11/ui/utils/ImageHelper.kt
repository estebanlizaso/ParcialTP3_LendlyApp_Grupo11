package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils

import ort.tp3.parcialtp3_lendlyapp_grupo11.R

object ImageHelper {
    /**
     * Retorna el ID del recurso local si el nombre coincide con marcas conocidas,
     * de lo contrario retorna null.
     */
    fun getLocalBrandLogo(brandName: String): Int? {
        val nameLower = brandName.lowercase()
        return when {
            nameLower.contains("apple") -> R.drawable.brand_apple
            nameLower.contains("samsung") -> R.drawable.brand_samsung
            nameLower.contains("nike") -> R.drawable.brand_nike
            nameLower.contains("sony") -> R.drawable.brand_sony
            else -> null
        }
    }

    /**
     * Retorna el ID del recurso local para productos específicos.
     */
    fun getLocalProductImage(productName: String): Int? {
        val nameLower = productName.lowercase()
        return when {
            nameLower.contains("iphone") || nameLower.contains("phone") -> R.drawable.phone
            nameLower.contains("headphones") || nameLower.contains("sony") -> R.drawable.headphones
            nameLower.contains("mac") || nameLower.contains("laptop") -> R.drawable.mac
            else -> null
        }
    }
}
