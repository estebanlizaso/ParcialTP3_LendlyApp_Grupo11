package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.mapper.toDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductBrandDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.ShopRepository
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val _brands = MutableStateFlow<List<ProductBrandDto>>(emptyList())
    val brands: StateFlow<List<ProductBrandDto>> = _brands

    private val _featuredProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val featuredProducts: StateFlow<List<ProductDto>> = _featuredProducts

    private val _recommendedProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val recommendedProducts: StateFlow<List<ProductDto>> = _recommendedProducts

    private val _allProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val allProducts: StateFlow<List<ProductDto>> = _allProducts

    private val _avatarUrl = MutableStateFlow<String>("")
    val avatarUrl: StateFlow<String> = _avatarUrl

    init {
        observeRepository()
        fetchData()
    }

    private fun observeRepository() {
        shopRepository.brands.onEach { brandEntities ->
            _brands.value = brandEntities.map { it.toDto() }
        }.launchIn(viewModelScope)

        shopRepository.products.onEach { productEntities ->
            _allProducts.value = productEntities.map { it.toDto() }
        }.launchIn(viewModelScope)

        shopRepository.bestSellers.onEach { productEntities ->
            _featuredProducts.value = productEntities.map { it.toDto() }
        }.launchIn(viewModelScope)

        shopRepository.recommended.onEach { productEntities ->
            _recommendedProducts.value = productEntities.map { it.toDto() }
        }.launchIn(viewModelScope)
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                // Fetch user for avatar
                val userResponse = repository.getUser()
                _avatarUrl.value = userResponse.user.avatar
            } catch (e: Exception) {
                Log.e("ShopViewModel", "Error fetching data", e)
            }
        }
    }
}
