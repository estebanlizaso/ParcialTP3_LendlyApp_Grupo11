package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@Composable
fun LoanSuccessScreen(
    viewModel: LoanViewModel,
    onDone: () -> Unit
) {
    val selectedOption by viewModel.selectedLoanOption.collectAsState()
    val appliedAmount by viewModel.appliedAmount.collectAsState()
    val selectedLenderName by viewModel.selectedLenderName.collectAsState()
    val appliedDateTime by viewModel.appliedDateTime.collectAsState()
    val appliedTransactionNumber by viewModel.appliedTransactionNumber.collectAsState()

    TransactionPage(
        onClose = onDone,
        onDoneClick = onDone,
        transactionContent = {
            AppTransaction(
                amount = appliedAmount,
                statusText = stringResource(id = R.string.loan_success_added_to_account),
                originText = "From $selectedLenderName",
                typeLabel = stringResource(id = R.string.loan_success_type_label)
            )
        },
        detailsContent = {
            AppTransactionDetails(
                title = stringResource(id = R.string.loan_success_details_title),
                details = listOf(
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_monthly_fee),
                        selectedOption?.rightValue ?: stringResource(id = R.string.loan_success_monthly_fee_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_interest),
                        selectedOption?.subtitle ?: stringResource(id = R.string.loan_success_interest_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_installment_plan),
                        selectedOption?.title ?: stringResource(id = R.string.loan_success_installment_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_date_time),
                        appliedDateTime
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_transaction_number),
                        appliedTransactionNumber.ifEmpty { stringResource(id = R.string.loan_success_transaction_number_value) },
                        isBold = true
                    )
                ),
                onHelpCenterClick = { /* TODO */ }
            )
        }
    )
}
