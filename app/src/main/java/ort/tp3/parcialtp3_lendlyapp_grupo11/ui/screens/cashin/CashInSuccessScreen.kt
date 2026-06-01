package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun CashInSuccessScreen(
    sourceName: String,
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    onCloseClick: () -> Unit = onDoneClick
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
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
                CircleTextButton(text = "X", onClick = onCloseClick)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "i", color = BlackFont, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.size(24.dp))
                Text(text = "...", color = BlackFont, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(34.dp))
            TransactionIconBadge(
                type = TransactionIconType.ADD,
                badgeSize = 64.dp,
                iconSize = 30.dp,
                highlighted = true
            )

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Added to your account",
                color = GrayColor,
                fontSize = 14.sp,
                fontFamily = interSemiBold
            )
            Text(
                text = "2,500.00 PHP",
                color = BlackFont,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold
            )
            Text(
                text = "From $sourceName",
                color = GrayColor,
                fontSize = 14.sp,
                fontFamily = interSemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))
            AppButton(
                text = "Cash-In",
                type = ButtonType.OUTLINED,
                fillMaxWidth = false,
                height = 34.dp,
                horizontalPadding = 16.dp,
                borderColor = GrayColor,
                backgroundColor = Color.Transparent,
                textColor = GrayColor
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Text(
                text = "Transaction Details",
                color = BlackFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            TransactionDetailRow(label = "Transfer Fee", value = "-P15.00")
            Spacer(modifier = Modifier.height(18.dp))
            TransactionDetailRow(label = "Date & Time", value = "Jul 15, 2024 9:12 AM")
            Spacer(modifier = Modifier.height(18.dp))
            TransactionDetailRow(label = "Transaction Number", value = "#200412312551", highlighted = true)
            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider(color = Color(0xFFE5E0E0))
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Need help?\nGo to Help Center",
                color = Color(0xFF526E37),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            text = "Done",
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = onDoneClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun CashInSuccessScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        CashInSuccessScreen(sourceName = "GCash")
    }
}
