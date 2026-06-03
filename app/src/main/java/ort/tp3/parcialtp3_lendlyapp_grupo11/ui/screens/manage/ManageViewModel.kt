package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import retrofit2.HttpException
import java.io.IOException

data class ManageUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val user: UserDto? = null,
    val errorMessage: String? = null
)

class ManageViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    var uiState by mutableStateOf(ManageUiState())
        private set

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        uiState = ManageUiState(isLoading = true)
        viewModelScope.launch {
            try {
                // usamos ID 1 para probar
                val response = repository.getUser(id = 1)

                if (response.success) {
                    uiState = ManageUiState(
                        isSuccess = true,
                        user = response.user
                    )
                } else {
                    uiState = ManageUiState(errorMessage = "Could not load user profile")
                }
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Session expired"
                    404 -> "User not found"
                    500 -> "Server error"
                    else -> "Error: ${e.code()}"
                }
                uiState = ManageUiState(errorMessage = message)
            } catch (e: IOException) {
                uiState = ManageUiState(errorMessage = "No internet connection")
            } catch (e: Exception) {
                uiState = ManageUiState(errorMessage = e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
