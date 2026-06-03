package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Locale
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.CalendarIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
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
                modifier = Modifier.background(Color.White),
                onLeftClick = onBack,
                rightIcon = { CalendarIcon(onClick = { /* TODO */ }) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Text(
                text = "Active loans",
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )

            when (val state = uiState) {
                is LoanUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Green)
                    }
                }
                is LoanUiState.Success -> {
                    val activeLoans = state.data.loans.filter { it.status.equals("ACTIVE", ignoreCase = true) }
                    val historyLoans = state.data.loans.filter { it.status.equals("PAID", ignoreCase = true) }.take(3)

                    if (activeLoans.isEmpty() && historyLoans.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No loans found",
                                style = TextStyle(fontFamily = interFonts, color = GrayText)
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 24.dp)
                        ) {
                        // Present Section
                        item {
                            SectionHeader(title = "Present")
                        }
                        items(activeLoans) { loan ->
                            ActiveLoanItem(loan)
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.5.dp,
                                color = GreyDivider
                            )
                        }

                        // Recent Loans Section
                        item {
                            Spacer(Modifier.height(32.dp))
                            SectionHeader(title = "Recent Loans")
                        }
                        items(historyLoans) { loan ->
                            RecentLoanItem(loan)
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.5.dp,
                                color = GreyDivider
                            )
                        }
                        }
                    }
                }
                is LoanUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message, color = Color.Red, fontFamily = interFonts)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Column {
        HorizontalDivider(thickness = 1.dp, color = GreyDivider)
        Text(
            text = title,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = GrayText
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ActiveLoanItem(loan: LoanDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFF9F9F9), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = loan.lenderLogo,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit,
                error = painterResource(id = R.drawable.brand_apple) // Fallback
            )
        }
        
        Spacer(Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = loan.lender,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontSize = 12.sp,
                    color = GrayText
                )
            )
            Text(
                text = loan.purpose,
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
        
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Fees of february",
                style = TextStyle(
                    fontFamily = interFonts,
                    fontSize = 12.sp,
                    color = GrayText
                )
            )
            Text(
                text = "${String.format(Locale.US, "%,.0f", loan.installmentAmount)} PHP",
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun RecentLoanItem(loan: LoanDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFFFDF7F7), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = loan.startDate,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontSize = 12.sp,
                    color = GrayText
                )
            )
            Text(
                text = loan.purpose,
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
        
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = loan.lender,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontSize = 12.sp,
                    color = GrayText
                )
            )
            Text(
                text = loan.status,
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
    }
}
