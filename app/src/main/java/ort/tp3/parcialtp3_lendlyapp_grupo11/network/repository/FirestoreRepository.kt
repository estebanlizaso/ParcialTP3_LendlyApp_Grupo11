package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.FirestoreTransaction
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
     * @return ID generado por Firestore, o null si no se pudo guardar.
     */
    suspend fun saveLoan(uid: String, loan: ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto): String? {
        return try {
            val document = db.collection("users").document(uid)
                .collection("loans")
                .add(loan)
                .await()

            document.id
        } catch (e: Exception) {
            null
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
                    totalInstallments = doc.getLong("totalInstallments")?.toInt() ?: 0
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Guarda una transacción en la sub-colección 'transactions' del usuario.
     * @return ID generado por Firestore, o null si no se pudo guardar.
     */
    suspend fun saveTransaction(uid: String, transaction: FirestoreTransaction): String? {
        return try {
            val transactionData = hashMapOf(
                "type" to transaction.type,
                "title" to transaction.title,
                "description" to transaction.description,
                "amount" to transaction.amount,
                "currency" to transaction.currency,
                "status" to transaction.status,
                "date" to transaction.date,
                "dateKey" to transaction.dateKey,
                "loanId" to transaction.loanId,
                "sourceName" to transaction.sourceName,
                "sourceKey" to transaction.sourceKey,
                "sourceLogoUrl" to transaction.sourceLogoUrl,
                "referenceNumber" to transaction.referenceNumber
            )

            val document = db.collection("users").document(uid)
                .collection("transactions")
                .add(transactionData)
                .await()

            document.id
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Obtiene todas las transacciones del usuario desde Firestore.
     */
    suspend fun getUserTransactions(uid: String): List<FirestoreTransaction> {
        return try {
            val snapshot = db.collection("users").document(uid)
                .collection("transactions")
                .get()
                .await()

            snapshot.documents
                .map { it.toFirestoreTransaction() }
                .sortedByDescending { it.date }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Obtiene las transacciones de un usuario para un día específico.
     * @param dateKey Fecha en formato yyyy-MM-dd.
     */
    suspend fun getUserTransactionsByDate(uid: String, dateKey: String): List<FirestoreTransaction> {
        return try {
            val snapshot = db.collection("users").document(uid)
                .collection("transactions")
                .whereEqualTo("dateKey", dateKey)
                .get()
                .await()

            snapshot.documents
                .map { it.toFirestoreTransaction() }
                .sortedByDescending { it.date }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun DocumentSnapshot.toFirestoreTransaction(): FirestoreTransaction {
        val date = getString("date") ?: ""

        return FirestoreTransaction(
            id = id,
            type = getString("type") ?: "",
            title = getString("title") ?: "",
            description = getString("description") ?: "",
            amount = getDouble("amount") ?: getLong("amount")?.toDouble() ?: 0.0,
            currency = getString("currency") ?: "PHP",
            status = getString("status") ?: "COMPLETED",
            date = date,
            dateKey = getString("dateKey") ?: date.take(10),
            loanId = getString("loanId"),
            sourceName = getString("sourceName") ?: "",
            sourceKey = getString("sourceKey") ?: "",
            sourceLogoUrl = getString("sourceLogoUrl"),
            referenceNumber = getString("referenceNumber") ?: ""
        )
    }
}
