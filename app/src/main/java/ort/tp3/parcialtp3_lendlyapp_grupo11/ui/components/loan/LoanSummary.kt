package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
import java.util.Locale

@Composable
fun LoanSummary(
    amount: String,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
    lenderName: String = "Rayland Partners",
    processingFeePercentage: Double = ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanBusinessRules.PROCESSING_FEE_PERCENTAGE,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {

    val amountValue = amount.replace(",", "").toDoubleOrNull() ?: 0.0
    val processingFeeValue = amountValue * (processingFeePercentage / 100.0)
    val totalToReceive = amountValue

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(SplashScreenGreen)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.loan_apply_summary),
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    letterSpacing = 0.sp,
                    color = Color.Black
                )
            )
            
            Spacer(Modifier.height(16.dp))
            
            SummaryRow(
                label = stringResource(R.string.loan_apply_amount_label),
                value = String.format(Locale.US, "PHP %.2f", amountValue)
            )
            SummaryRow(
                label = stringResource(R.string.loan_apply_fee_label),
                value = String.format(Locale.US, "-%.2f", processingFeeValue)
            )
            
            HorizontalDivider(Modifier.padding(vertical = 12.dp))
            
            SummaryRow(
                label = stringResource(R.string.loan_apply_total_receive),
                value = String.format(Locale.US, "₱ %.2f", totalToReceive),
                isBold = true
            )
            SummaryRow(stringResource(R.string.loan_apply_lender), lenderName)
            
            Text(
                text = stringResource(R.string.loan_what_is_this),
                color = DarkGreen,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(Modifier.height(24.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(8.dp))
            }

            AppButton(
                text = stringResource(R.string.loan_get_loan_button),
                onClick = onApplyClick,
                modifier = Modifier.fillMaxWidth(),
                isLoading = isLoading
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left text
        Text(
            text = label,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
                color = DarkGreyText
            )
        )

        Text(
            text = value,
            style = if (isBold) {
                TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    color = BlackFont
                )
            } else {
                TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.5.sp,
                    color = GrayText
                )
            }
        )
    }
}
