package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.AuthRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager

// definimos estados posibles
sealed class LoginUiState {
    object Idle : LoginUiState() // estado inicial
    object Loading : LoginUiState() // cargando
    object Success : LoginUiState() // exitoso
    data class Error(val message: String) : LoginUiState() // error contraseña o mail
}

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    // stateflow para observar cambios de estado
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // validaciones locales
    fun isValidPhone(phone: String): Boolean {
        return phone.isNotBlank() && phone.length >= 8
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 4
    }

    // "Log In" presionado
    fun login(phone: String, password: String) {
        if (!isValidPhone(phone) || !isValidPassword(password)) {
            _uiState.value = LoginUiState.Error("Please enter valid credentials")
            return
        }

        // pasamos a "Loading"
        _uiState.value = LoginUiState.Loading

        // corrutina
        viewModelScope.launch {
            try {
                val request = LoginRequestDto(phone = phone, password = password)
                val response = repository.login(request)

                if (response.success) {
                    sessionManager.saveToken(response.token)
                    _uiState.value = LoginUiState.Success
                } else {
                    _uiState.value = LoginUiState.Error("Invalid phone or password")
                }
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.localizedMessage ?: "Network Error. Please try again.")
            }
        }
    }

    // reiniciar estado si usuario descarta un error
    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}