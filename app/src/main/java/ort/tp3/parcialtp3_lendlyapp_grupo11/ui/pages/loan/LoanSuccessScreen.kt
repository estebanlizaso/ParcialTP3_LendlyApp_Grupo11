package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanSuccessScreen(
    onDone: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Green, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(40.dp))
            }
            
            Spacer(Modifier.height(16.dp))
            Text("Added to your account", fontSize = 14.sp, color = Color.Gray)
            Text("2,000.00 PHP", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("From Apple Inc.", fontSize = 14.sp, color = Color.Gray)
            
            Spacer(Modifier.height(16.dp))
            Surface(
                color = Color(0xFFF9F9F9),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
            ) {
                Text("Loan Amount", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 14.sp)
            }

            Spacer(Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Transaction Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                
                DetailRow("Monthly Fee", "₱ 982.12")
                DetailRow("Interest", "2.99%")
                DetailRow("Installment plan", "6 Months")
                DetailRow("Date & Time", "Jul 15, 2024 9:12 AM")
                DetailRow("Transaction Number", "#200412312551", isLink = true)
                
                Spacer(Modifier.height(48.dp))
                
                Text("Need help?", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 14.sp, color = Color.Gray)
                Text("Go to Help Center", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }

            Spacer(Modifier.weight(1f))

            AppButton(
                text = "Done",
                onClick = onDone,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isLink: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 16.sp)
        Text(
            value,
            color = if (isLink) DarkGreen else Color.Black,
            fontSize = 16.sp,
            fontWeight = if (isLink) FontWeight.Bold else FontWeight.Normal,
            textDecoration = if (isLink) androidx.compose.ui.text.style.TextDecoration.Underline else null
        )
    }
}
