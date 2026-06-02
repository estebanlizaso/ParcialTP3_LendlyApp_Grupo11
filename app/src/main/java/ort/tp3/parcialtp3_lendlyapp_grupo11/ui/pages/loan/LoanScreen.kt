package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green

@Composable
fun LoanScreen(
    onNavigateToApply: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp) // Space for bottom bar if needed
    ) {
        // Top Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF90EE90), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Surface(
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.onboarding_pay_easily), // Replace with clock icon if available
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Limited Time Offer", fontSize = 12.sp)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Safe and\nsecure loans",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Text(
                    "All here in Rayland",
                    fontSize = 14.sp,
                    color = DarkGreen.copy(alpha = 0.7f)
                )
            }
            // Image could go here on the right
        }

        // Borrow limit
        Column(modifier = Modifier.padding(16.dp)) {
            Text("You can borrow up to", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("₱ 30,000.00", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            Text("*Subject to evaluation", fontSize = 12.sp, color = Color.Gray)

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Loan Details", fontWeight = FontWeight.Bold)
                Text("What is this?", color = Green, fontSize = 14.sp)
            }
            
            Spacer(Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                DetailItem("Payable in", "6 - 12", "months", Modifier.weight(1f))
                DetailItem("Interest Rate", "1.99%", "ave per mo.", Modifier.weight(1f))
                DetailItem("Process Fee", "3%", "as low as", Modifier.weight(1f))
            }
        }

        // How it works
        Column(modifier = Modifier.padding(16.dp)) {
            Text("How it works", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            
            Row(Modifier.fillMaxWidth()) {
                WorkCard("Keep your credit score high", "The offered loan amount is based on your credit score", Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                WorkCard("Get instant approval", "Everything we need to process is already in the application", Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                WorkCard("Easy payments option available", "Skip the queue and pay your due on the application", Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                WorkCard("Safe and secure", "Rayland is working with trusted partners to provide this services", Modifier.weight(1f))
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

@Composable
fun DetailItem(title: String, value: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(title, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
        Text(subtitle, fontSize = 10.sp, color = Color.Gray)
    }
}

@Composable
fun WorkCard(title: String, description: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(Modifier.size(40.dp).background(Color.LightGray, RoundedCornerShape(4.dp)))
            Spacer(Modifier.height(8.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 18.sp)
            Spacer(Modifier.height(4.dp))
            Text(description, fontSize = 10.sp, color = Color.Gray, lineHeight = 14.sp)
        }
    }
}
