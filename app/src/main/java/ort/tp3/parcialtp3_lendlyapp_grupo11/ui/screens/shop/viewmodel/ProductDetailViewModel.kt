package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {
    private val _product = MutableStateFlow<ProductDto?>(null)
    val product: StateFlow<ProductDto?> = _product

    fun fetchProduct(productId: String) {
        viewModelScope.launch {
            // Primero intentamos buscar en la cache del repositorio
            val cachedProduct = shopRepository.getProductById(productId)
            
            if (cachedProduct != null) {
                _product.value = cachedProduct
            } else {
                // Si no esta en cache (ej: abrimos la app directamente en el detalle), cargamos la API
                try {
                    shopRepository.getProducts()
                    _product.value = shopRepository.getProductById(productId)
                } catch (e: Exception) {
                    // Manejar error
                }
            }
        }
    }
}
