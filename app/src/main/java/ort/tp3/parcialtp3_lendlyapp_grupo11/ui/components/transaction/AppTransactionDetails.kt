package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.MediumDarkGreenText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

data class TransactionDetail(
    val label: String,
    val value: String,
    val isBold: Boolean = false
)

@Composable
fun AppTransactionDetails(
    title: String = "Transaction Details",
    details: List<TransactionDetail>,
    onHelpCenterClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
                color = BlackIcon
            )
        )
        
        Spacer(Modifier.height(16.dp))

        details.forEach { detail ->
            DetailRow(detail.label, detail.value, detail.isBold)
        }

        Spacer(Modifier.height(48.dp))

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.loan_success_need_help),
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = stringResource(id = R.string.loan_success_go_to_help_center),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onHelpCenterClick
                ),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = GrayText
            )
        )

        Text(
            text = value,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = if (isBold) MediumDarkGreenText else GrayText,
                textDecoration = if (isBold) TextDecoration.Underline else null
            )
        )
    }
}
