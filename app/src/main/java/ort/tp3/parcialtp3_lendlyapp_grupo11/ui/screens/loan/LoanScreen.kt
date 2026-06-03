package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*

@Composable
fun LoanScreen(
    onNavigateToApply: () -> Unit,
    avatarUrl: String = ""
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Top Bar
        HomeTopBar(
            avatarUrl = avatarUrl,
            modifier = Modifier.padding(16.dp)
        )

        // Promotion Card
        InfoContainer(
            isProminent = true,
            tagText = "Limited Time Offer",
            tagIcon = painterResource(id = R.drawable.ic_alarm),
            titleText = "Safe and\nsecure loans",
            subtitleText = "All here in Rayland",
            imagePainter = painterResource(id = R.drawable.loan_info),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 12.dp)
        )

        // Loan Limit Card
        InfoContainer(
            borrowAmount = "₱ 30,000.00",
            onWhatIsThisClick = { /* TODO: Navigate */ },
            details = listOf(
                LoanDetailData("Payable in", "6 - 12", "months"),
                LoanDetailData("Interest Rate", "1.99%", "ave per mo."),
                LoanDetailData("Process Fee", "3%", "as low as")
            ),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        // How it works section
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "How it works",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(modifier = Modifier.fillMaxWidth()) {
                HIWCard(
                    title = "Keep your credit score high",
                    description = "The offered loan amount is based on your credit score",
                    image = painterResource(id = R.drawable.loan_how_credit_score),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                HIWCard(
                    title = "Get instant approval",
                    description = "Everything we need to process is already in the application",
                    image = painterResource(id = R.drawable.loan_how_approval),
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                HIWCard(
                    title = "Easy payments option available",
                    description = "Skip the queue and pay your due on the application",
                    image = painterResource(id = R.drawable.loan_how_easy_payments),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                HIWCard(
                    title = "Safe and secure",
                    description = "Rayland is working with trusted partners to provide this services",
                    image = painterResource(id = R.drawable.loan_how_safe_secure),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        AppButton(
            text = "Get This Loan",
            onClick = onNavigateToApply,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
