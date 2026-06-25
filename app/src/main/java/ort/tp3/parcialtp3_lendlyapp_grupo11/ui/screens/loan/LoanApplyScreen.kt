package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.AmountInputField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.AppDropdownSelector
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.LoanOptionData
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.LoanSummary
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.SelectableLoanOption
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.StepBox
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanApplyUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun LoanApplyScreen(
    viewModel: LoanViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    var amount by remember { mutableStateOf("2,000.00") }
    val plans = listOf(
        LoanOptionData("6 Months", "2.99% Interest", "₱ 982.12/mo"),
        LoanOptionData("12 Months", "1.99% Interest", "₱ 491.06/mo")
    )
    var selectedPlan by remember { mutableStateOf(plans[0]) }
    var selectedPurpose by remember { mutableStateOf("Educational") }
    
    val applyState by viewModel.applyState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(applyState) {
        when (applyState) {
            is LoanApplyUiState.Success -> {
                onSuccess()
                viewModel.resetApplyState()
            }
            is LoanApplyUiState.Error -> {
                snackbarHostState.showSnackbar((applyState as LoanApplyUiState.Error).message)
                viewModel.resetApplyState()
            }
            is LoanApplyUiState.ValidationSuccess -> {
                // Si la validación local pasa, procedemos con el préstamo real
                val amountValue = amount.replace(",", "").toDoubleOrNull() ?: 0.0
                viewModel.applyLoan(amountValue, selectedPlan, selectedPurpose)
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            AppTopBar(
                onLeftClick = onBack,
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
                .padding(vertical = 16.dp)
        ) {

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
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

                StepBox(
                    stepTag = stringResource(R.string.loan_apply_step_1),
                    description = stringResource(R.string.loan_apply_enter_amount)
                ) {
                    AmountInputField(
                        value = amount,
                        onValueChange = { amount = it }
                    )
                }
                
                HorizontalDivider(Modifier.padding(bottom = 16.dp))

                StepBox(
                    stepTag = stringResource(R.string.loan_apply_step_2),
                    description = stringResource(R.string.loan_apply_select_plan)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        plans.forEach { plan ->
                            SelectableLoanOption(
                                data = plan,
                                isSelected = selectedPlan.title == plan.title,
                                onClick = { selectedPlan = plan }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(28.dp))

                StepBox(
                    stepTag = stringResource(R.string.loan_apply_step_3),
                    description = stringResource(R.string.loan_apply_select_purpose)
                ) {
                    AppDropdownSelector(
                        options = listOf("Educational", "Business", "Personal", "Medical"),
                        selectedOption = selectedPurpose,
                        onOptionSelected = { selectedPurpose = it }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Summary
            LoanSummary(
                amount = amount,
                onApplyClick = {
                    viewModel.applyLoan(
                        amount.replace(",", "").toDouble(),
                        selectedPlan,
                        selectedPurpose
                    )
                    val amountValue = amount.replace(",", "").toDoubleOrNull() ?: 0.0
                    viewModel.validateLoanRequest(amountValue)
                },
                isLoading = applyState is LoanApplyUiState.Loading,
                errorMessage = null // Ya no lo mostramos aquí, usamos snackbar
            )
        }
    }
}
