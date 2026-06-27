package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

/**
 * Transaction stored under users/{uid}/transactions in Cloud Firestore.
 * dateKey uses yyyy-MM-dd so notifications can query activity by day.
 */
data class FirestoreTransaction(
    val id: String = "",
    val type: String = "",
    val title: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val currency: String = "PHP",
    val status: String = "COMPLETED",
    val date: String = "",
    val dateKey: String = "",
    val loanId: String? = null,
    val sourceName: String = "",
    val sourceKey: String = "",
    val sourceLogoUrl: String? = null,
    val referenceNumber: String = ""
)
