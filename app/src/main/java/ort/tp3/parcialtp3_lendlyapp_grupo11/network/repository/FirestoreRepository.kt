package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserScoring

/**
 * Repositorio para manejar la comunicación con Cloud Firestore.
 */
class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Obtiene el scoring de un usuario específico desde la colección 'users'.
     * @param uid El identificador único del usuario (UID de Firebase Auth).
     * @return Objeto UserScoring o null si hay un error o no existe.
     */
    suspend fun getUserScoring(uid: String): UserScoring? {
        return try {
            val document = db.collection("users").document(uid).get().await()
            if (document.exists()) {
                // Mapeo manual para evitar errores de tipo Long vs Double de Firestore
                UserScoring(
                    creditScore = document.getLong("creditScore")?.toInt() ?: 0,
                    loanLimit = document.getDouble("loanLimit") ?: document.getLong("loanLimit")?.toDouble() ?: 0.0,
                    availableBalance = document.getDouble("availableBalance") ?: document.getLong("availableBalance")?.toDouble() ?: 0.0,
                    eligible = document.getBoolean("eligible") ?: false
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Crea un perfil de scoring inicial para un nuevo usuario.
     * @param uid El UID del usuario recién registrado.
     * @param initialBalance El saldo inicial del usuario.
     */
    suspend fun createInitialScoring(uid: String, initialBalance: Double) {
        try {
            val initialScoring = UserScoring(
                creditScore = 500,
                loanLimit = 15000.0,
                availableBalance = initialBalance,
                eligible = true
            )
            db.collection("users").document(uid).set(initialScoring).await()
        } catch (e: Exception) {
            // En producción aquí se debería loguear el error
        }
    }

    /**
     * Actualiza el saldo del usuario en Firestore.
     * @param uid El UID del usuario.
     * @param newBalance El nuevo saldo a guardar.
     */
    suspend fun updateBalance(uid: String, newBalance: Double) {
        try {
            db.collection("users").document(uid)
                .update("availableBalance", newBalance)
                .await()
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    /**
     * Guarda un nuevo préstamo en la sub-colección 'loans' del usuario.
     */
    suspend fun saveLoan(uid: String, loan: ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto) {
        try {
            db.collection("users").document(uid)
                .collection("loans")
                .add(loan)
                .await()
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    /**
     * Obtiene todos los préstamos del usuario desde Firestore.
     */
    suspend fun getUserLoans(uid: String): List<ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto> {
        return try {
            val snapshot = db.collection("users").document(uid)
                .collection("loans")
                .get()
                .await()
            
            snapshot.documents.mapNotNull { doc ->
                ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto(
                    id = doc.id,
                    lender = doc.getString("lender") ?: "",
                    lenderLogo = doc.getString("lenderLogo") ?: "",
                    amount = doc.getDouble("amount") ?: 0.0,
                    amountDue = doc.getDouble("amountDue") ?: 0.0,
                    installmentAmount = doc.getDouble("installmentAmount") ?: 0.0,
                    installmentPlan = doc.getString("installmentPlan") ?: "",
                    interestRate = doc.getDouble("interestRate") ?: 0.0,
                    purpose = doc.getString("purpose") ?: "",
                    status = doc.getString("status") ?: "ACTIVE",
                    nextPaymentDate = doc.getString("nextPaymentDate"),
                    nextPaymentLabel = doc.getString("nextPaymentLabel"),
                    startDate = doc.getString("startDate") ?: "",
                    endDate = doc.getString("endDate") ?: "",
                    paidInstallments = doc.getLong("paidInstallments")?.toInt() ?: 0,
                    totalInstallments = doc.getLong("totalInstallments")?.toInt() ?: 0,
                    transactionNumber = doc.getString("transactionNumber") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Actualiza un préstamo existente en Firestore.
     */
    suspend fun updateLoan(uid: String, loan: ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto) {
        try {
            if (loan.id.isEmpty()) return
            
            db.collection("users").document(uid)
                .collection("loans")
                .document(loan.id)
                .set(loan)
                .await()
        } catch (e: Exception) {
            // Manejo de error
        }
    }
}
