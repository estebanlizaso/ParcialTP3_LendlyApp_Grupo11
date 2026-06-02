package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

// estados posibles
sealed class ManageUiState {
    object Loading : ManageUiState()
    data class Success(val user: UserDto) : ManageUiState() // Si sale bien, nos guarda el UserDto entero
    data class Error(val message: String) : ManageUiState()
}

class ManageViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ManageUiState>(ManageUiState.Loading)
    val uiState: StateFlow<ManageUiState> = _uiState.asStateFlow()

    init {
        // apenas se crea la pantalla, cargamos perfil
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = ManageUiState.Loading
            try {
                // usamos ID 1 para probar
                val response = repository.getUser(id = 1)

                if (response.success) {
                    _uiState.value = ManageUiState.Success(response.user)
                } else {
                    _uiState.value = ManageUiState.Error("Failed to load user profile.")
                }
            } catch (e: Exception) {
                _uiState.value = ManageUiState.Error(e.localizedMessage ?: "Network error.")
            }
        }
    }
}