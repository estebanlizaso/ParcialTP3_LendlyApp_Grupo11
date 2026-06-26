package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository
import javax.inject.Inject

@HiltViewModel
class CashInViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    var selectedSource by mutableStateOf("BPI")
    
    var isLoading by mutableStateOf(false)
        private set

    fun performCashIn(amount: Double, onSuccess: () -> Unit) {
        val uid = sessionManager.getToken() ?: return
        
        viewModelScope.launch {
            isLoading = true
            try {
                // 1. Obtener balance actual de la fuente de verdad local (Room)
                val user = userDao.getUserById(uid).firstOrNull()
                val currentBalance = user?.accountBalance ?: 0.0
                val newBalance = currentBalance + amount

                // 2. Actualizar en Firestore (Nube)
                firestoreRepository.updateBalance(uid, newBalance)

                // 3. Actualizar en Room (Local) para feedback instantáneo
                userDao.updateAccountBalance(uid, newBalance)
                
                onSuccess()
            } catch (e: Exception) {
                // Manejar error de red o base de datos
            } finally {
                isLoading = false
            }
        }
    }
}
