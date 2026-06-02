package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.AuthRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager

// estados para la UI del registro
sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // registro de usuario
    fun register(
        firstName: String,
        lastName: String,
        day: String,
        month: String,
        year: String,
        address: String,
        city: String,
        postalCode: String,
        countryCode: String,
        phone: String,
        password: String
    ) {
        // validaciones
        if (firstName.isBlank() || lastName.isBlank() || address.isBlank() || city.isBlank() || postalCode.isBlank()) {
            _uiState.value = RegisterUiState.Error("Please fill in all required fields.")
            return
        }

        if (phone.length < 8) {
            _uiState.value = RegisterUiState.Error("Please enter a valid phone number.")
            return
        }

        if (password.length < 6) {
            _uiState.value = RegisterUiState.Error("Password must be at least 6 characters long.")
            return
        }

        // armar telefono y fecha
        val fullPhoneNumber = "$countryCode$phone"
        val dateOfBirth = "$year-$month-$day"

        // pasamos a "Loading"
        _uiState.value = RegisterUiState.Loading

        // corrutina
        viewModelScope.launch {
            try {
                val request = RegisterRequestDto(
                    firstName = firstName,
                    lastName = lastName,
                    dateOfBirth = dateOfBirth,
                    address = address,
                    city = city,
                    postalCode = postalCode,
                    phone = fullPhoneNumber,
                    password = password
                )

                val response = repository.register(request)

                if (response.success) {
                    // guardamos token
                    sessionManager.saveToken(response.token)
                    _uiState.value = RegisterUiState.Success
                } else {
                    _uiState.value = RegisterUiState.Error("Registration failed. Please try again.")
                }
            } catch (e: Exception) {
                _uiState.value = RegisterUiState.Error(e.localizedMessage ?: "Network Error. Please try again.")
            }
        }
    }

    // reiniciar estado si usuario descarta un error
    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }
}