package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppSearchBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OptionListItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ScreenTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

private data class PaymentOption(
    val title: String,
    val iconText: String,
    val iconBackgroundColor: Color,
    val iconTextColor: Color = Color.White,
    val iconResId: Int? = null
)

@Composable
fun OnlineCashInOptionsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onOptionClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    val banks = listOf(
        PaymentOption("BPI", "BP", Color(0xFFC62828), iconResId = R.drawable.logo_bpi),
        PaymentOption("Chinabank", "CB", Color(0xFFFF1F1F), iconResId = R.drawable.logo_chinabank),
        PaymentOption("RCBC", "RC", Color(0xFF5AA5DE), iconResId = R.drawable.logo_rcbc),
        PaymentOption("Unionbank", "UB", Color(0xFFFF6D1A), iconResId = R.drawable.logo_unionbank)
    )
    val eWallets = listOf(
        PaymentOption("GCash", "G", Color(0xFF1E88E5), iconResId = R.drawable.logo_gcash),
        PaymentOption("Pay Maya", "PM", Color(0xFF00B875), iconResId = R.drawable.logo_paymaya),
        PaymentOption("PayPal", "P", Color.White, Color(0xFF006CCB), iconResId = R.drawable.logo_paypal)
    )

    val filteredBanks = banks.filter { it.title.contains(searchQuery, ignoreCase = true) }
    val filteredEWallets = eWallets.filter { it.title.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenLight3)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        ScreenTopBar(
            title = "",
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Online Cash-In Options",
            color = BlackFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(18.dp))
        AppSearchBar(
            value = searchQuery,
            onValueChange = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(14.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                PaymentSectionTitle(text = "BANKS")
                filteredBanks.forEach { option ->
                    PaymentOptionItem(
                        option = option,
                        onClick = { onOptionClick(option.title) }
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFFE9E4E4)
                )

                PaymentSectionTitle(text = "E-WALLETS")
                filteredEWallets.forEach { option ->
                    PaymentOptionItem(
                        option = option,
                        onClick = { onOptionClick(option.title) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentSectionTitle(text: String) {
    Text(
        text = text,
        color = BlackFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = interFonts,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
private fun PaymentOptionItem(
    option: PaymentOption,
    onClick: () -> Unit
) {
    OptionListItem(
        title = option.title,
        iconText = option.iconText,
        iconBackgroundColor = option.iconBackgroundColor,
        iconTextColor = option.iconTextColor,
        iconResId = option.iconResId,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
fun OnlineCashInOptionsScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        OnlineCashInOptionsScreen()
    }
}
