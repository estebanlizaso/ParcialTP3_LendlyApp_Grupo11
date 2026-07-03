package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import java.io.InputStreamReader
import javax.inject.Inject

data class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    private val firestoreRepository: ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository,
    private val firestore: FirebaseFirestore,
    @param:ApplicationContext private val context: Context
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
    var email by mutableStateOf("")
    var city by mutableStateOf("")
    var postalCode by mutableStateOf("")
    var countryCode by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")

    // Error states
    var firstNameError by mutableStateOf<String?>(null)
    var lastNameError by mutableStateOf<String?>(null)
    var dayError by mutableStateOf<String?>(null)
    var monthError by mutableStateOf<String?>(null)
    var yearError by mutableStateOf<String?>(null)
    var addressError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
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
        emailError = null
        cityError = null
        postalCodeError = null
        countryCodeError = null
        phoneError = null

        firstNameError = if (firstName.isBlank()) "Required field" else null
        lastNameError = if (lastName.isBlank()) "Required field" else null
        addressError = if (address.isBlank()) "Required field" else null
        
        emailError = when {
            email.isBlank() -> "Required field"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }

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
                      emailError == null &&
                      cityError == null && 
                      postalCodeError == null && 
                      countryCodeError == null && 
                      phoneError == null

        return isValid
    }

    fun validatePhoneNumber(): Boolean {
        countryCodeError = when {
            countryCode.isBlank() -> "Required"
            !countryCode.all { it.isDigit() } -> "Only numbers"
            countryCode.length > 3 -> "Max 3 digits"
            else -> null
        }

        phoneError = when {
            phone.isBlank() -> "Required"
            !phone.all { it.isDigit() } -> "Only numbers"
            phone.length > 8 -> "Max 8 digits"
            phone.length < 7 -> "Min 7 digits"
            else -> null
        }

        return countryCodeError == null && phoneError == null
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

        uiState = RegisterUiState(isLoading = true)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid
                    if (uid != null) {
                        sessionManager.saveToken(uid)
                        viewModelScope.launch {
                            // En registro, si por casualidad el UID ya existe en Room (sembrado), lo respetamos
                            val existingUser = userDao.getUserById(uid).firstOrNull()
                            
                            if (existingUser == null) {
                                val initialData = getInitialUserData(context)
                                val initialBalance = initialData["accountBalance"] as? Double ?: 25000.0
                                
                                // Crear scoring y balance inicial en Firestore
                                firestoreRepository.createInitialScoring(uid, initialBalance)
                                
                                // Obtener el scoring recién creado de Firestore para sincronizar Room
                                val scoring = firestoreRepository.getUserScoring(uid)

                                // Guardar datos personales en Firestore (Merge)
                                val profileData = hashMapOf(
                                    "firstName" to firstName,
                                    "lastName" to lastName,
                                    "email" to email,
                                    "birthDay" to day,
                                    "birthMonth" to month,
                                    "birthYear" to year,
                                    "address" to address,
                                    "city" to city,
                                    "postalCode" to postalCode,
                                    "countryCode" to countryCode,
                                    "phoneNumber" to phone
                                )
                                
                                try {
                                    firestore.collection("users").document(uid)
                                        .set(profileData, SetOptions.merge())
                                        .await()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                                userDao.insertUser(
                                    UserEntity(
                                        uid = uid,
                                        email = email,
                                        fullName = "$firstName $lastName",
                                        avatar = initialData["avatar"] as? String ?: "https://i.pravatar.cc/150?img=3",
                                        creditScore = scoring?.creditScore ?: 500,
                                        accountBalance = scoring?.availableBalance ?: initialBalance,
                                        birthDate = "$year-$month-$day",
                                        address = "$address, $city",
                                        phone = "+$countryCode-$phone"
                                    )
                                )
                            }
                            uiState = RegisterUiState(isSuccess = true)
                        }
                    } else {
                        uiState = RegisterUiState(errorMessage = "Registration failed")
                    }
                } else {
                    val message = when (val exception = task.exception) {
                        is com.google.firebase.auth.FirebaseAuthUserCollisionException -> "This email is already in use"
                        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> "The email address is badly formatted"
                        else -> exception?.localizedMessage ?: "Registration failed"
                    }
                    uiState = RegisterUiState(errorMessage = message)
                }
            }
    }

    fun resetState() {
        uiState = RegisterUiState()
    }

    private fun getInitialUserData(context: Context): Map<String, Any> {
        return try {
            val inputStream = context.assets.open("user_initial_data.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = Gson().fromJson(reader, type)
            reader.close()
            data
        } catch (e: Exception) {
            e.printStackTrace()
            emptyMap()
        }
    }
}
