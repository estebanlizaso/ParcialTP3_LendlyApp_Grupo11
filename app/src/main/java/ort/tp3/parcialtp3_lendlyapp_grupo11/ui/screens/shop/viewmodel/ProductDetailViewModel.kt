package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.mapper.toDto
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
            // Buscamos directamente en la base de datos (Room)
            val productEntity = shopRepository.getProductById(productId)
            _product.value = productEntity?.toDto()
        }
    }
}
