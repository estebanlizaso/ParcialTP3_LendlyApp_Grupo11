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
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.FirestoreTransaction
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.CashInSourceCatalog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
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

                // 3. Guardar la transacción para notificaciones por fecha
                val now = Date()
                val sourceKey = CashInSourceCatalog.keyFromName(selectedSource)
                val transactionId = firestoreRepository.saveTransaction(
                    uid = uid,
                    transaction = FirestoreTransaction(
                        type = "CASH_IN",
                        title = "Added balance",
                        description = "Cash in from $selectedSource",
                        amount = amount,
                        currency = "PHP",
                        status = "COMPLETED",
                        date = formatTransactionDate(now),
                        dateKey = formatTransactionDateKey(now),
                        sourceName = selectedSource,
                        sourceKey = sourceKey,
                        referenceNumber = generateReferenceNumber(now)
                    )
                )
                transactionId?.let { id ->
                    sessionManager.addUnreadNotificationId(uid, id)
                }

                // 4. Actualizar en Room (Local) para feedback instantáneo
                userDao.updateAccountBalance(uid, newBalance)

                onSuccess()
            } catch (e: Exception) {
                // Manejar error de red o base de datos
            } finally {
                isLoading = false
            }
        }
    }

    private fun formatTransactionDate(date: Date): String {
        return utcFormatter("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date)
    }

    private fun formatTransactionDateKey(date: Date): String {
        return utcFormatter("yyyy-MM-dd").format(date)
    }

    private fun generateReferenceNumber(date: Date): String {
        return "#${date.time}"
    }

    private fun utcFormatter(pattern: String): SimpleDateFormat {
        return SimpleDateFormat(pattern, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
