package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.CalendarIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.utils.ImageHelper
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun LoanHistoryScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit,
    onLoanClick: (LoanDto) -> Unit
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
                            SectionHeader(title = stringResource(id = R.string.loan_history_pending_payments))
                        }
                        items(activeLoans) { loan ->
                            ActiveLoanItem(loan, onClick = { onLoanClick(loan) })
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.5.dp,
                                color = GreyDivider
                            )
                        }

                        // Recent Loans Section
                        item {
                            Spacer(Modifier.height(32.dp))
                            SectionHeader(title = stringResource(id = R.string.loan_history_paid_loans))
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
fun ActiveLoanItem(loan: LoanDto, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
            var isError by remember { mutableStateOf(false) }
            val localLogo = remember(loan.lender) { ImageHelper.getLocalBrandLogo(loan.lender) }

            if (isError && localLogo != null) {
                Image(
                    painter = painterResource(id = localLogo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            } else {
                AsyncImage(
                    model = loan.lenderLogo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (isError && localLogo == null) Modifier.border(0.5.dp, Color.Red, CircleShape) else Modifier
                        ),
                    contentScale = ContentScale.Fit,
                    onState = { state ->
                        isError = state is coil.compose.AsyncImagePainter.State.Error
                    }
                )
            }
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
            val nextPaymentLabel = remember(loan.nextPaymentLabel, loan.startDate, loan.paidInstallments) {
                loan.nextPaymentLabel ?: calculateNextPaymentLabel(loan.startDate, loan.paidInstallments)
            }
            Text(
                text = nextPaymentLabel,
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

private fun calculateNextPaymentLabel(startDate: String, paidInstallments: Int): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = sdf.parse(startDate) ?: return "Cuota pendiente"
        val calendar = Calendar.getInstance()
        calendar.time = date
        // La cuota 1 se paga al mes siguiente de la fecha de inicio
        calendar.add(Calendar.MONTH, paidInstallments + 1)

        val monthFormat = SimpleDateFormat("MMMM", Locale.US)
        val monthName = monthFormat.format(calendar.time)
        "Fee of ${monthName.replaceFirstChar { it.uppercase() }}"
    } catch (e: Exception) {
        "Pending fee"
    }
}
