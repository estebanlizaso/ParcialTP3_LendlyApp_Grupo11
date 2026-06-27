package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils

import androidx.annotation.DrawableRes
import java.util.Locale
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

object CashInSourceCatalog {
    fun keyFromName(name: String): String {
        return name.lowercase(Locale.US).filter { it.isLetterOrDigit() }
    }

    @DrawableRes
    fun logoResId(sourceKey: String): Int? {
        return when (sourceKey) {
            "bpi" -> R.drawable.logo_bpi
            "chinabank" -> R.drawable.logo_chinabank
            "rcbc" -> R.drawable.logo_rcbc
            "unionbank" -> R.drawable.logo_unionbank
            "gcash" -> R.drawable.logo_gcash
            "paymaya" -> R.drawable.logo_paymaya
            "paypal" -> R.drawable.logo_paypal
            "7eleven" -> R.drawable.logo_7_eleven
            "cebuanalhuillier" -> R.drawable.logo_cebuana_lhuillier
            "lbc" -> R.drawable.logo_lbc
            "mlhuillier" -> R.drawable.logo_m_lhuillier
            else -> null
        }
    }
}
