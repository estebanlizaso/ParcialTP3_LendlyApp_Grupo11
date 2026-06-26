package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

/**
 * Configuración de negocio para un plan de préstamo.
 * @param months Cantidad de cuotas/meses.
 * @param interestRate Tasa de interés mensual en porcentaje (ej: 2.99).
 * @param label Etiqueta para mostrar en la UI.
 */
data class LoanProductConfig(
    val months: Int,
    val interestRate: Double,
    val label: String
)

/**
 * Configuración de negocio para un prestamista según el propósito.
 */
data class LenderConfig(
    val name: String,
    val logo: String
)

/**
 * Repositorio centralizado de reglas de negocio para planes de préstamos.
 * Aquí es donde se editan los valores de meses e intereses de toda la app.
 */
object LoanBusinessRules {
    const val PROCESSING_FEE_PERCENTAGE = 3.0

    val defaultPlans = listOf(
        LoanProductConfig(months = 6, interestRate = 2.99, label = "6 Months"),
        LoanProductConfig(months = 12, interestRate = 1.99, label = "12 Months")
    )

    val lendersByPurpose = mapOf(
        "Shopping" to LenderConfig("Samsung", "https://favicon.im/samsung.com?larger=true"),
        "Electronics" to LenderConfig("Apple Inc.", "https://favicon.im/apple.com?larger=true"),
        "Sports" to LenderConfig("Nike Inc.", "https://favicon.im/nike.com?larger=true"),
        "Educational" to LenderConfig("ORT", "https://favicon.im/ort.edu.ar?larger=true"),
        "Personal" to LenderConfig("Naranja X", "https://favicon.im/naranjax.com?larger=true"),
        "Business" to LenderConfig("Banco Galicia", "https://favicon.im/galicia.ar?larger=true"),
        "Medical" to LenderConfig("Swiss Medical", "https://favicon.im/swissmedical.com.ar?larger=true")
    )
}
