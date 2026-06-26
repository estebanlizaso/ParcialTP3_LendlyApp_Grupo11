package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

/**
 * Modelo de datos para representar el scoring del usuario en Firestore.
 * @param creditScore El puntaje crediticio calculado (0 a 850).
 * @param loanLimit El monto máximo que el usuario puede solicitar.
 * @param availableBalance El saldo disponible del usuario.
 * @param eligible Indica si el usuario está habilitado para pedir préstamos.
 */
data class UserScoring(
    val creditScore: Int = 0,
    val loanLimit: Double = 0.0,
    val availableBalance: Double = 0.0,
    val eligible: Boolean = false
)
