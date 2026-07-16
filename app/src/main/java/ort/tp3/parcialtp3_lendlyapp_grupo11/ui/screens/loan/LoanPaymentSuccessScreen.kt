package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanApplyUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun LoanPaymentSuccessScreen(
    viewModel: LoanViewModel,
    onClose: () -> Unit,
    onSuccess: () -> Unit
) {
    val selectedLoan by viewModel.selectedLoanForPayment.collectAsState()
    val paymentDateTime by viewModel.appliedDateTime.collectAsState()
    val paymentTransactionNumber by viewModel.appliedTransactionNumber.collectAsState()
    val paymentState by viewModel.paymentState.collectAsState()

    LaunchedEffect(paymentState) {
        if (paymentState is LoanApplyUiState.Success) {
            onSuccess()
        }
    }

    val amountStr = selectedLoan?.let { 
        String.format(java.util.Locale.US, "%,.2f", it.installmentAmount) 
    } ?: "0.00"
    
    val installmentText = selectedLoan?.let {
        "${it.paidInstallments + 1} of ${it.totalInstallments}"
    } ?: ""

    TransactionPage(
        onClose = onClose,
        onDoneClick = { if (paymentState !is LoanApplyUiState.Loading) viewModel.confirmPayment() },
        doneButtonText = if (paymentState is LoanApplyUiState.Loading) "Processing..." else stringResource(id = R.string.loan_payment_confirm_button),
        transactionContent = {
            AppTransaction(
                amount = amountStr,
                statusText = stringResource(id = R.string.loan_payment_debited_from_account),
                originText = "To ${selectedLoan?.lender ?: ""}",
                typeLabel = stringResource(id = R.string.loan_payment_type_label)
            )
            
            if (paymentState is LoanApplyUiState.Error) {
                Text(
                    text = (paymentState as LoanApplyUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },
        detailsContent = {
            AppTransactionDetails(
                title = stringResource(id = R.string.loan_payment_details_title),
                details = listOf(
                    TransactionDetail(
                        stringResource(id = R.string.loan_payment_installment_number),
                        installmentText
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_date_time),
                        paymentDateTime
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_transaction_number),
                        paymentTransactionNumber,
                        isBold = true
                    )
                ),
                onHelpCenterClick = { /* TODO */ }
            )
        }
    )
}
