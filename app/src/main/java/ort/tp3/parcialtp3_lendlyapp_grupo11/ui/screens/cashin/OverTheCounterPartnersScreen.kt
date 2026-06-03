package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.OptionListItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ScreenTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

private data class CashInPartner(
    val name: String,
    val iconText: String,
    val iconColor: Color,
    val iconResId: Int
)

@Composable
fun OverTheCounterPartnersScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onPartnerClick: (String) -> Unit = {}
) {
    val partners = listOf(
        CashInPartner("7-Eleven", "7", Color(0xFF00A859), R.drawable.logo_7_eleven),
        CashInPartner("Cebuana Lhuillier", "C", Color(0xFF073F59), R.drawable.logo_cebuana_lhuillier),
        CashInPartner("LBC", "LBC", Color(0xFFE91E4D), R.drawable.logo_lbc),
        CashInPartner("M Lhuillier", "ML", Color(0xFFFF1F1F), R.drawable.logo_m_lhuillier)
    )

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
            text = "Over-The-Counter Partners",
            color = BlackFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(28.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                partners.forEach { partner ->
                    OptionListItem(
                        title = partner.name,
                        subtitle = "Max. Transaction amount \$5,000",
                        iconText = partner.iconText,
                        iconBackgroundColor = partner.iconColor,
                        iconTextColor = Color.White,
                        iconResId = partner.iconResId,
                        onClick = { onPartnerClick(partner.name) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverTheCounterPartnersScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        OverTheCounterPartnersScreen()
    }
}
