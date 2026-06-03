package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.AuthRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import retrofit2.HttpException
import java.io.IOException

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun login(phone: String, password: String) {
        // Validaciones locales
        if (phone.isBlank() || password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Please fill in all fields")
            return
        }

        if (phone.length < 8) {
            uiState = uiState.copy(errorMessage = "Phone must be at least 8 digits long")
            return
        }



        uiState = LoginUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val request = LoginRequestDto(phone = phone, password = password)
                val response = repository.login(request)

                if (response.success) {
                    sessionManager.saveToken(response.token)
                    uiState = LoginUiState(isSuccess = true)
                } else {
                    uiState = LoginUiState(errorMessage = "Incorrect phone or password")
                }
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    401 -> "Invalid credentials"
                    400 -> "Incorrect data"
                    500 -> "Server error, please try again later"
                    else -> "Unexpected error: ${e.code()}"
                }
                uiState = LoginUiState(errorMessage = message)
            } catch (e: IOException) {
                uiState = LoginUiState(errorMessage = "No internet connection")
            } catch (e: Exception) {
                uiState = LoginUiState(errorMessage = e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        uiState = LoginUiState()
    }
}
