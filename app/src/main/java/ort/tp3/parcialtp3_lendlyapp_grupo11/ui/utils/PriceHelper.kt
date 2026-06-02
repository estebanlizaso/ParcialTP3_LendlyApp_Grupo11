package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils

import java.text.NumberFormat
import java.util.Locale

object PriceHelper {
    /**
     * Formatea un valor double a un string de moneda con el signo $ y separador de miles.
     * Ejemplo: 1200.0 -> $1,200
     */
    fun formatPrice(price: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        formatter.maximumFractionDigits = 0
        return formatter.format(price).replace("USD", "$").replace(" ", "")
    }
}
