package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.CalendarIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun LoanHistoryScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.loansState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLoans()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = onBack,
                rightIcon = { CalendarIcon(onClick = { /* TODO */ }) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Text("Active loans", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))

            when (val state = uiState) {
                is LoanUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LoanUiState.Success -> {
                    val activeLoans = state.data.loans.filter { it.status == "Active" }
                    val historyLoans = state.data.loans.filter { it.status == "Paid" }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Text("Present", fontSize = 14.sp, color = Color.Gray)
                            Spacer(Modifier.height(16.dp))
                        }
                        items(activeLoans) { loan ->
                            ActiveLoanItem(loan)
                        }
                        
                        item {
                            Spacer(Modifier.height(24.dp))
                            Text("Recent Loans", fontSize = 14.sp, color = Color.Gray)
                            Spacer(Modifier.height(16.dp))
                        }
                        items(historyLoans) { loan ->
                            HistoryLoanItem(loan)
                        }
                    }
                }
                is LoanUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message, color = Color.Red)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ActiveLoanItem(loan: LoanDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF9F9F9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for lender logo
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(loan.lender, fontSize = 12.sp, color = Color.Gray)
            Text(loan.purpose, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("Fees of febuary", fontSize = 12.sp, color = Color.Gray)
            Text("${loan.installmentAmount} PHP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HistoryLoanItem(loan: LoanDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF9F9F9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(loan.startDate, fontSize = 12.sp, color = Color.Gray)
            Text(loan.purpose, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(loan.lender, fontSize = 12.sp, color = Color.Gray)
            Text(loan.status, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
