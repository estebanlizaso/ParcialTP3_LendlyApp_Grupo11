package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import java.io.InputStreamReader
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun login(email: String, password: String) {
        // Validaciones locales
        if (email.isBlank() || password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Please fill in all fields")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uiState = uiState.copy(errorMessage = "Please enter a valid email")
            return
        }

        uiState = LoginUiState(isLoading = true)

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid
                    if (uid != null) {
                        sessionManager.saveToken(uid)
                        viewModelScope.launch {
                            // Primero buscamos si el usuario ya existe en Room (sembrado o previo)
                            val existingUser = userDao.getUserById(uid).firstOrNull()
                            
                            if (existingUser == null) {
                                // Si no existe (es un nuevo usuario fuera del sembrado), creamos uno por defecto
                                val initialData = getInitialUserData(context)
                                userDao.insertUser(
                                    UserEntity(
                                        uid = uid,
                                        email = email,
                                        fullName = initialData["fullName"] as? String ?: "User",
                                    avatar = initialData["avatar"] as? String ?: "https://i.pravatar.cc/150?img=3",
                                    creditScore = (initialData["creditScore"] as? Double)?.toInt() ?: 720,
                                        accountBalance = initialData["accountBalance"] as? Double ?: 25000.0,
                                        birthDate = initialData["birthDate"] as? String,
                                        address = initialData["address"] as? String,
                                        phone = initialData["phone"] as? String ?: ""
                                    )
                                )
                            }
                            // Si ya existe (como los UIDs sembrados), lo dejamos tal cual está en Room
                            uiState = LoginUiState(isSuccess = true)
                        }
                    } else {
                        uiState = LoginUiState(errorMessage = "User not found")
                    }
                } else {
                    val message = when (val exception = task.exception) {
                        is com.google.firebase.auth.FirebaseAuthInvalidUserException -> "User does not exist"
                        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -> "Incorrect email or password"
                        else -> exception?.localizedMessage ?: "Login failed"
                    }
                    uiState = LoginUiState(errorMessage = message)
                }
            }
    }

    fun resetState() {
        uiState = LoginUiState()
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
            emptyMap()
        }
    }
}
