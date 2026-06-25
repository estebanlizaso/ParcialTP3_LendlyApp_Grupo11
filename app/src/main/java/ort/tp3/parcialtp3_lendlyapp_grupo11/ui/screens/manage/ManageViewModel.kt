package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import java.io.InputStreamReader
import java.io.IOException

data class ManageUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val user: UserEntity? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ManageViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var uiState by mutableStateOf(ManageUiState())
        private set

    init {
        loadUserProfile()
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
                val inputStream = context.assets.open("initial_users.json")
                val reader = InputStreamReader(inputStream)
                val users: List<UserEntity> = Gson().fromJson(reader, object : TypeToken<List<UserEntity>>() {}.type)
                reader.close()
                
                val currentUserData = users.find { it.uid == uid }
                if (currentUserData != null) {
                    userDao.updateCreditScore(uid, currentUserData.creditScore)
                    userDao.updateAvatar(uid, currentUserData.avatar)
                }
            } catch (e: Exception) {
                // Ignore sync errors
            }

            userDao.getUserById(uid).collectLatest { user ->
                if (user != null) {
                    uiState = ManageUiState(
                        isSuccess = true,
                        user = user
                    )
                } else {
                    // Si no está en Room, intentamos cargar de la API como fallback o mostrar error
                    uiState = ManageUiState(errorMessage = "User profile not found in local database")
                }
            }
        }
    }
}
