package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.transaction.*

@Composable
fun LoanSuccessScreen(
    onDone: () -> Unit
) {
    TransactionPage(
        onClose = onDone,
        onDoneClick = onDone,
        transactionContent = {
            AppTransaction(
                amount = stringResource(id = R.string.loan_success_amount_value),
                statusText = stringResource(id = R.string.loan_success_added_to_account),
                originText = stringResource(id = R.string.loan_success_origin),
                typeLabel = stringResource(id = R.string.loan_success_type_label)
            )
        },
        detailsContent = {
            AppTransactionDetails(
                title = stringResource(id = R.string.loan_success_details_title),
                details = listOf(
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_monthly_fee),
                        stringResource(id = R.string.loan_success_monthly_fee_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_interest),
                        stringResource(id = R.string.loan_success_interest_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_installment_plan),
                        stringResource(id = R.string.loan_success_installment_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_date_time),
                        stringResource(id = R.string.loan_success_date_time_value)
                    ),
                    TransactionDetail(
                        stringResource(id = R.string.loan_success_transaction_number),
                        stringResource(id = R.string.loan_success_transaction_number_value),
                        isBold = true
                    )
                ),
                onHelpCenterClick = { /* TODO */ }
            )
        }
    )
}
