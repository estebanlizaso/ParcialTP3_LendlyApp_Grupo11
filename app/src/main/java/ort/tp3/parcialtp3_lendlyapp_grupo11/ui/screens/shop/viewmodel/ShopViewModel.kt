package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductBrandDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository
import android.util.Log
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

class ShopViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val mockBrands = listOf(
        ProductBrandDto("apple", "Apple", "https://img.logo.dev/apple.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
        ProductBrandDto("samsung", "Samsung", "https://img.logo.dev/samsung.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
        ProductBrandDto("nike", "Nike", "https://img.logo.dev/nike.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true"),
        ProductBrandDto("sony", "Sony", "https://img.logo.dev/sony.com?token=pk_dM8WXsJYTDmCwtB4k9ynrA&retina=true")
    )
    
    private val _brands = MutableStateFlow<List<ProductBrandDto>>(mockBrands)
    val brands: StateFlow<List<ProductBrandDto>> = _brands

    private val _featuredProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val featuredProducts: StateFlow<List<ProductDto>> = _featuredProducts

    private val _allProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val allProducts: StateFlow<List<ProductDto>> = _allProducts

    private val _avatarUrl = MutableStateFlow<String>("")
    val avatarUrl: StateFlow<String> = _avatarUrl

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                // Fetch user for avatar
                val userResponse = repository.getUser()
                _avatarUrl.value = userResponse.user.avatar

                // Fetch products
                val response = ShopRepository.getProducts()
                if (response.brands.isNotEmpty()) {
                    _brands.value = response.brands
                }
                _featuredProducts.value = response.featured
                _allProducts.value = response.products
            } catch (e: Exception) {
                Log.e("ShopViewModel", "Error fetching data", e)
            }
        }
    }
}
