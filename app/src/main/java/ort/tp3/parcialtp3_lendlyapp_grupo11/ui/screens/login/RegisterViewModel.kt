package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.AuthRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import retrofit2.HttpException
import java.io.IOException

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())
        private set

    // Form states
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var day by mutableStateOf("")
    var month by mutableStateOf("")
    var year by mutableStateOf("")
    var address by mutableStateOf("")
    var city by mutableStateOf("")
    var postalCode by mutableStateOf("")
    var countryCode by mutableStateOf("63") 
    var phone by mutableStateOf("")
    var password by mutableStateOf("")

    // Error states
    var firstNameError by mutableStateOf<String?>(null)
    var lastNameError by mutableStateOf<String?>(null)
    var dayError by mutableStateOf<String?>(null)
    var monthError by mutableStateOf<String?>(null)
    var yearError by mutableStateOf<String?>(null)
    var addressError by mutableStateOf<String?>(null)
    var cityError by mutableStateOf<String?>(null)
    var postalCodeError by mutableStateOf<String?>(null)
    var countryCodeError by mutableStateOf<String?>(null)
    var phoneError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    fun validateProfileDetails(): Boolean {
        // Clear previous errors
        firstNameError = null
        lastNameError = null
        dayError = null
        monthError = null
        yearError = null
        addressError = null
        cityError = null
        postalCodeError = null
        countryCodeError = null
        phoneError = null

        firstNameError = if (firstName.isBlank()) "Required field" else null
        lastNameError = if (lastName.isBlank()) "Required field" else null
        addressError = if (address.isBlank()) "Required field" else null
        cityError = if (city.isBlank()) "Required field" else null

        dayError = when {
            day.isBlank() -> "Required field"
            !day.all { it.isDigit() } -> "Must contain only numbers"
            day.toInt() !in 1..31 -> "Day must be between 1 and 31"
            else -> null
        }
        monthError = when {
            month.isBlank() -> "Required field"
            !month.all { it.isDigit() } -> "Must contain only numbers"
            month.toInt() !in 1..12 -> "Month must be between 1 and 12"
            else -> null
        }
        yearError = when {
            year.isBlank() -> "Required field"
            !year.all { it.isDigit() } -> "Must contain only numbers"
            year.toInt() !in 1900..2024 -> "Invalid year"
            else -> null
        }
        postalCodeError = when {
            postalCode.isBlank() -> "Required field"
            !postalCode.all { it.isDigit() } -> "Must contain only numbers"
            else -> null
        }
        countryCodeError = when {
            countryCode.isBlank() -> "Required field"
            !countryCode.all { it.isDigit() } -> "Must contain only numbers"
            else -> null
        }

        phoneError = when {
            phone.isBlank() -> "Required field"
            !phone.all { it.isDigit() } -> "Must contain only numbers"
            phone.length < 8 -> "Invalid phone length"
            else -> null
        }

        val isValid = firstNameError == null && 
                      lastNameError == null && 
                      dayError == null && 
                      monthError == null && 
                      yearError == null && 
                      addressError == null && 
                      cityError == null && 
                      postalCodeError == null && 
                      countryCodeError == null && 
                      phoneError == null

        return isValid
    }

    fun register() {
        // Clear password error
        passwordError = null
        
        // Final password validation before sending
        val isPasswordValid = password.length >= 9 && 
                             password.any { it.isLetter() } && 
                             password.any { it.isDigit() }

        if (!isPasswordValid) {
            passwordError = "Password must be at least 9 characters long, containing a letter and a number"
            return
        }

        val fullPhoneNumber = "+$countryCode$phone"
        val dateOfBirth = "$year-$month-$day"

        uiState = RegisterUiState(isLoading = true)

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
                    sessionManager.saveToken(response.token)
                    uiState = RegisterUiState(isSuccess = true)
                } else {
                    uiState = RegisterUiState(errorMessage = "Registration failed, please try again")
                }
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "Invalid registration data"
                    409 -> "User already exists"
                    500 -> "Server error, please try again later"
                    else -> "Unexpected error: ${e.code()}"
                }
                uiState = RegisterUiState(errorMessage = message)
            } catch (e: IOException) {
                uiState = RegisterUiState(errorMessage = "No internet connection")
            } catch (e: Exception) {
                uiState = RegisterUiState(errorMessage = e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        uiState = RegisterUiState()
    }
}
