package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.StepBox
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanApplyUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanApplyScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    var amount by remember { mutableStateOf("2000") }
    var selectedPlan by remember { mutableStateOf("6 Months") }
    var selectedPurpose by remember { mutableStateOf("Educational") }
    
    val applyState by viewModel.applyState.collectAsState()

    LaunchedEffect(applyState) {
        if (applyState is LoanApplyUiState.Success) {
            onSuccess()
            viewModel.resetApplyState()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                onBackClick = onBack,
                centerText = stringResource(id = R.string.loan_title),
                rightIcon = { InfoIcon(onClick = { /* TODO */ }) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.loan_apply_header_title),
                style = TextStyle(
                    fontFamily = montserratFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    color = Color.Black
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.loan_apply_header_subtitle),
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    color = Color.Black
                )
            )

            Spacer(Modifier.height(32.dp))

            StepBox(stepTag = stringResource(R.string.loan_apply_step_1), description = stringResource(R.string.loan_apply_enter_amount)) {
                Text(
                    "₱ ${amount}.00",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
            
            HorizontalDivider(Modifier.padding(vertical = 16.dp))

            StepBox(stepTag = stringResource(R.string.loan_apply_step_2), description = stringResource(R.string.loan_apply_select_plan)) {
                InstallmentPlanItem(
                    title = "6 Months",
                    interest = "2.99% Interest",
                    monthly = "₱ 982.12/mo",
                    isSelected = selectedPlan == "6 Months",
                    onClick = { selectedPlan = "6 Months" }
                )
            }

            Spacer(Modifier.height(24.dp))

            StepBox(stepTag = stringResource(R.string.loan_apply_step_3), description = stringResource(R.string.loan_apply_select_purpose)) {
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF9F9F9), RoundedCornerShape(8.dp))
                        .clickable { expanded = true }
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(selectedPurpose)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        listOf("Educational", "Business", "Personal", "Medical").forEach { purpose ->
                            DropdownMenuItem(
                                text = { Text(purpose) },
                                onClick = {
                                    selectedPurpose = purpose
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Summary
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0FFF0), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(24.dp)
            ) {
                Text(text = stringResource(R.string.loan_apply_summary), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                SummaryRow(stringResource(R.string.loan_apply_amount_label), "PHP $amount.00")
                SummaryRow(stringResource(R.string.loan_apply_fee_label), "-150.00")
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                SummaryRow(stringResource(R.string.loan_apply_total_receive), "₱ $amount.00", isBold = true)
                SummaryRow(stringResource(R.string.loan_apply_lender), "null")
                Text(stringResource(R.string.loan_what_is_this), color = DarkGreen, fontSize = 14.sp, textDecoration = null, modifier = Modifier.padding(top = 8.dp))
                
                Spacer(Modifier.height(24.dp))
                
                if (applyState is LoanApplyUiState.Error) {
                    Text((applyState as LoanApplyUiState.Error).message, color = Color.Red, fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                }

                AppButton(
                    text = stringResource(R.string.loan_get_loan_button),
                    onClick = { viewModel.applyLoan(amount.toDouble(), selectedPlan, selectedPurpose) },
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = applyState is LoanApplyUiState.Loading
                )
            }
        }
    }
}

@Composable
fun InstallmentPlanItem(title: String, interest: String, monthly: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) BorderStroke(1.dp, Green) else null,
        color = Color(0xFFF9F9F9)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(interest, fontSize = 12.sp, color = Color.Gray)
            }
            Text(monthly, fontWeight = FontWeight.Bold, color = DarkGreen)
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
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(
            value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isBold) 18.sp else 14.sp,
            color = if (isBold) DarkGreen else Color.Black
        )
    }
}
