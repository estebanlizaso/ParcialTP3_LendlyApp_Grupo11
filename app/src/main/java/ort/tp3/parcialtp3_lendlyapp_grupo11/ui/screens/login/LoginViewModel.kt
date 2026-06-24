package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager
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
                        uiState = LoginUiState(isSuccess = true)
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
}
