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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*
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
                AmountInputField(
                    value = amount,
                    onValueChange = { amount = it }
                )
            }
            
            HorizontalDivider(Modifier.padding(bottom = 16.dp))

            StepBox(stepTag = stringResource(R.string.loan_apply_step_2), description = stringResource(R.string.loan_apply_select_plan)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val plans = listOf(
                        LoanOptionData("6 Months", "2.99% Interest", "₱ 982.12/mo"),
                        LoanOptionData("12 Months", "1.99% Interest", "₱ 491.06/mo")
                    )
                    plans.forEach { plan ->
                        SelectableLoanOption(
                            data = plan,
                            isSelected = selectedPlan == plan.title,
                            onClick = { selectedPlan = plan.title }
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            StepBox(stepTag = stringResource(R.string.loan_apply_step_3), description = stringResource(R.string.loan_apply_select_purpose)) {
                AppDropdownSelector(
                    options = listOf("Educational", "Business", "Personal", "Medical"),
                    selectedOption = selectedPurpose,
                    onOptionSelected = { selectedPurpose = it }
                )
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
