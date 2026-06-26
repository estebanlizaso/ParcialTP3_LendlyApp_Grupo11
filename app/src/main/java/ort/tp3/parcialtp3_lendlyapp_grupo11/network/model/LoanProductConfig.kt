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
 * Repositorio centralizado de reglas de negocio para planes de préstamos.
 * Aquí es donde se editan los valores de meses e intereses de toda la app.
 */
object LoanBusinessRules {
    val defaultPlans = listOf(
        LoanProductConfig(months = 6, interestRate = 2.99, label = "6 Months"),
        LoanProductConfig(months = 12, interestRate = 1.99, label = "12 Months")
    )
}
