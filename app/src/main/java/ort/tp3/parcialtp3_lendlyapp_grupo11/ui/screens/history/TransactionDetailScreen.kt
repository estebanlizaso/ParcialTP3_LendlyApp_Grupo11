package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ButtonType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.CircleTextButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionDetailRow
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconBadge
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayColor
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun TransactionDetailScreen(
    uiState: TransactionDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val detail = uiState.detail

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (detail != null) {
            TransactionDetailContent(
                detail = detail,
                onBackClick = onBackClick
            )
        } else {
            TransactionDetailHeader(onBackClick = onBackClick)
            Text(
                text = if (uiState.isLoading) "Loading transaction..." else uiState.error.orEmpty(),
                color = if (uiState.error == null) GrayColor else Color.Red,
                fontSize = 14.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Composable
private fun TransactionDetailContent(
    detail: TransactionDetailUi,
    onBackClick: () -> Unit
) {
    TransactionDetailHeader(
        detail = detail,
        onBackClick = onBackClick
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Transaction Details",
            color = BlackFont,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(22.dp))
        TransactionDetailRow(label = "Fee", value = detail.fee)
        Spacer(modifier = Modifier.height(18.dp))
        TransactionDetailRow(label = "Date & Time", value = detail.dateTime)
        Spacer(modifier = Modifier.height(18.dp))
        TransactionDetailRow(
            label = "Transaction Number",
            value = detail.transactionNumber,
            highlighted = true
        )

        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(color = Color(0xFFE5E0E0))
        Spacer(modifier = Modifier.height(32.dp))
        HelpCenterText()
    }
}

@Composable
private fun TransactionDetailHeader(
    detail: TransactionDetailUi? = null,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GreenLight3)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleTextButton(text = "<", onClick = onBackClick)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "i", color = BlackFont, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.size(24.dp))
            Text(text = "...", color = BlackFont, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        if (detail != null) {
            Spacer(modifier = Modifier.height(34.dp))
            TransactionIconBadge(
                type = detail.iconType,
                badgeSize = 72.dp,
                iconSize = 32.dp,
                highlighted = true
            )

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = detail.title,
                color = GrayColor,
                fontSize = 14.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = detail.amount,
                color = BlackFont,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFonts
            )
            Text(
                text = detail.destination,
                color = GrayColor,
                fontSize = 14.sp,
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(14.dp))
            AppButton(
                text = detail.category,
                type = ButtonType.OUTLINED,
                fillMaxWidth = false,
                height = 34.dp,
                horizontalPadding = 16.dp,
                borderColor = GrayColor,
                backgroundColor = Color.Transparent,
                textColor = GrayColor
            )
        }
    }
}

@Composable
private fun HelpCenterText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Didn't find what you were looking for?",
            color = GrayColor,
            fontSize = 13.sp,
            fontFamily = interFonts,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Go to Help Center",
            color = Color(0xFF526E37),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        TransactionDetailScreen(
            uiState = TransactionDetailUiState(
                isLoading = false,
                detail = TransactionDetailUi(
                    iconType = TransactionIconType.PAYMENT,
                    title = "Paid this month",
                    amount = "1,255.00 PHP",
                    destination = "To Apple Inc.",
                    category = "Paid Bills",
                    fee = "₱100.00",
                    dateTime = "Jul 15, 2024 9:12 AM",
                    transactionNumber = "#200412312551"
                )
            )
        )
    }
}
