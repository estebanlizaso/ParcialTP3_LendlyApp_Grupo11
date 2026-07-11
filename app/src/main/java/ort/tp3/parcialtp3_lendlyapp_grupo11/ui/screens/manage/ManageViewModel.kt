package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanApplyUiState
import java.io.InputStreamReader

data class ManageUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val user: UserEntity? = null,
    val errorMessage: String? = null,
)

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    var uiState by mutableStateOf(ManageUiState())
        private set

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _day = MutableStateFlow("")
    val day: StateFlow<String> = _day.asStateFlow()

    private val _month = MutableStateFlow("")
    val month: StateFlow<String> = _month.asStateFlow()

    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private val _postalCode = MutableStateFlow("")
    val postalCode: StateFlow<String> = _postalCode.asStateFlow()

    private val _countryCode = MutableStateFlow("")
    val countryCode: StateFlow<String> = _countryCode.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    // estados de error individuales
    private val _firstNameError = MutableStateFlow<String?>(null)
    val firstNameError: StateFlow<String?> = _firstNameError.asStateFlow()

    private val _lastNameError = MutableStateFlow<String?>(null)
    val lastNameError: StateFlow<String?> = _lastNameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _dayError = MutableStateFlow<String?>(null)
    val dayError: StateFlow<String?> = _dayError.asStateFlow()

    private val _monthError = MutableStateFlow<String?>(null)
    val monthError: StateFlow<String?> = _monthError.asStateFlow()

    private val _yearError = MutableStateFlow<String?>(null)
    val yearError: StateFlow<String?> = _yearError.asStateFlow()

    private val _addressError = MutableStateFlow<String?>(null)
    val addressError: StateFlow<String?> = _addressError.asStateFlow()

    private val _cityError = MutableStateFlow<String?>(null)
    val cityError: StateFlow<String?> = _cityError.asStateFlow()

    private val _postalCodeError = MutableStateFlow<String?>(null)
    val postalCodeError: StateFlow<String?> = _postalCodeError.asStateFlow()

    private val _countryCodeError = MutableStateFlow<String?>(null)
    val countryCodeError: StateFlow<String?> = _countryCodeError.asStateFlow()

    private val _phoneError = MutableStateFlow<String?>(null)
    val phoneError: StateFlow<String?> = _phoneError.asStateFlow()

    private val _saveState = MutableStateFlow<LoanApplyUiState>(LoanApplyUiState.Idle)
    val saveState: StateFlow<LoanApplyUiState> = _saveState.asStateFlow()

    init {
        loadUserProfile()
        loadFirestoreProfile()
    }

    fun onFirstNameChange(value: String) { _firstName.value = value }
    fun onLastNameChange(value: String) { _lastName.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onDayChange(value: String) { _day.value = value }
    fun onMonthChange(value: String) { _month.value = value }
    fun onYearChange(value: String) { _year.value = value }
    fun onAddressChange(value: String) { _address.value = value }
    fun onCityChange(value: String) { _city.value = value }
    fun onPostalCodeChange(value: String) { _postalCode.value = value }
    fun onCountryCodeChange(value: String) { _countryCode.value = value }
    fun onPhoneNumberChange(value: String) { _phoneNumber.value = value }

    fun loadFirestoreProfile() {
        val uid = sessionManager.getToken() ?: return
        viewModelScope.launch {
            try {
                val document = firestore.collection("users").document(uid).get().await()
                if (document.exists()) {
                    _firstName.value = document.getString("firstName") ?: ""
                    _lastName.value = document.getString("lastName") ?: ""
                    _email.value = document.getString("email") ?: ""
                    _day.value = document.getString("birthDay") ?: ""
                    _month.value = document.getString("birthMonth") ?: ""
                    _year.value = document.getString("birthYear") ?: ""
                    _address.value = document.getString("address") ?: ""
                    _city.value = document.getString("city") ?: ""
                    _postalCode.value = document.getString("postalCode") ?: ""
                    _countryCode.value = document.getString("countryCode") ?: ""
                    _phoneNumber.value = document.getString("phoneNumber") ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveProfile() {
        val uid = sessionManager.getToken() ?: return

        // limpiar errores previos
        _firstNameError.value = null
        _lastNameError.value = null
        _emailError.value = null
        _addressError.value = null
        _dayError.value = null
        _monthError.value = null
        _yearError.value = null
        _cityError.value = null
        _postalCodeError.value = null
        _countryCodeError.value = null
        _phoneError.value = null

        // validaciones por campo
        var hasError = false

        if (_firstName.value.isBlank()) {
            _firstNameError.value = "Required field"
            hasError = true
        }
        if (_lastName.value.isBlank()) {
            _lastNameError.value = "Required field"
            hasError = true
        }
        if (_address.value.isBlank()) {
            _addressError.value = "Required field"
            hasError = true
        }
        if (_city.value.isBlank()) {
            _cityError.value = "Required field"
            hasError = true
        }

        val emailValue = _email.value
        if (emailValue.isBlank()) {
            _emailError.value = "Required field"
            hasError = true
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            _emailError.value = "Invalid email format"
            hasError = true
        }

        val d = _day.value.toIntOrNull()
        if (_day.value.isBlank()) {
            _dayError.value = "Required"
            hasError = true
        } else if (d == null || (d !in 1..31)) {
            _dayError.value = "Invalid"
            hasError = true
        }

        val m = _month.value.toIntOrNull()
        if (_month.value.isBlank()) {
            _monthError.value = "Required"
            hasError = true
        } else if (m == null || m !in 1..12) {
            _monthError.value = "Invalid"
            hasError = true
        }

        val y = _year.value.toIntOrNull()
        if (_year.value.isBlank()) {
            _yearError.value = "Required"
            hasError = true
        } else if (y == null || y !in 1900..2024) {
            _yearError.value = "Invalid"
            hasError = true
        }

        if (_postalCode.value.isBlank()) {
            _postalCodeError.value = "Required"
            hasError = true
        }

        if (_countryCode.value.isBlank()) {
            _countryCodeError.value = "Required"
            hasError = true
        }

        if (_phoneNumber.value.isBlank()) {
            _phoneError.value = "Required"
            hasError = true
        }

        if (hasError) return

        _saveState.value = LoanApplyUiState.Loading

        viewModelScope.launch {
            try {
                val profileData = hashMapOf(
                    "firstName" to _firstName.value,
                    "lastName" to _lastName.value,
                    "email" to _email.value,
                    "birthDay" to _day.value,
                    "birthMonth" to _month.value,
                    "birthYear" to _year.value,
                    "address" to _address.value,
                    "city" to _city.value,
                    "postalCode" to _postalCode.value,
                    "countryCode" to _countryCode.value,
                    "phoneNumber" to _phoneNumber.value
                )

                firestore.collection("users").document(uid)
                    .set(profileData, SetOptions.merge())
                    .await()

                _saveState.value = LoanApplyUiState.Success(LoanApplyResponse(success = true, message = "Profile updated successfully", loan = null))
            } catch (e: Exception) {
                e.printStackTrace()
                _saveState.value = LoanApplyUiState.Error(e.message ?: "Failed to update profile")
            }
        }
    }

    fun resetSaveState() {
        _saveState.value = LoanApplyUiState.Idle
    }

    fun loadUserProfile() {
        uiState = ManageUiState(isLoading = true)
        val uid = sessionManager.getToken()
        if (uid == null) {
            uiState = ManageUiState(errorMessage = "Session not found")
            return
        }

        viewModelScope.launch {
            // Sincronizamos con el JSON siempre para que se vea el score correcto del usuario actual
            try {
                context.assets.open("initial_users.json").use { inputStream ->
                    val reader = InputStreamReader(inputStream)
                    val users: List<UserEntity> = Gson().fromJson(reader, object : TypeToken<List<UserEntity>>() {}.type)
                    val currentUserData = users.find { it.uid == uid }
                    if (currentUserData != null) {
                        userDao.updateCreditScore(uid, currentUserData.creditScore)
                        userDao.updateAvatar(uid, currentUserData.avatar)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            userDao.getUserById(uid).collectLatest { user ->
                uiState = if (user != null) {
                    ManageUiState(
                        isSuccess = true,
                        user = user
                    )
                } else {
                    // Si no está en Room, intentamos cargar de la API como fallback o mostrar error
                    ManageUiState(errorMessage = "User profile not found in local database")
                }
            }
        }
    }
}
