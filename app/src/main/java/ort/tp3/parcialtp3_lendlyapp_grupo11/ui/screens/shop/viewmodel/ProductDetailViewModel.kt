package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository

class ProductDetailViewModel : ViewModel() {
    private val _product = MutableStateFlow<ProductDto?>(null)
    val product: StateFlow<ProductDto?> = _product

    fun fetchProduct(productId: String) {
        viewModelScope.launch {
            // Primero intentamos buscar en la cache del repositorio
            val cachedProduct = ShopRepository.getProductById(productId)
            
            if (cachedProduct != null) {
                _product.value = cachedProduct
            } else {
                // Si no esta en cache (ej: abrimos la app directamente en el detalle), cargamos la API
                try {
                    ShopRepository.getProducts()
                    _product.value = ShopRepository.getProductById(productId)
                } catch (e: Exception) {
                    // Manejar error
                }
            }
        }
    }
}
