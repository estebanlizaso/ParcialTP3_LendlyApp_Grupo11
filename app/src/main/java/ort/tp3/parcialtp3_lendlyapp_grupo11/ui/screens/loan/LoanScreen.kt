package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.loan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.*
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.HIWCard
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.InfoContainer
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.LoanDetailData

@Composable
fun LoanScreen(
    viewModel: LoanViewModel,
    onNavigateToApply: () -> Unit,
    onNotificationClick: () -> Unit = {}
) {
    val avatarUrl by viewModel.avatarUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        // Top Bar
        HomeTopBar(
            avatarUrl = avatarUrl,
            modifier = Modifier.padding(horizontal = 24.dp),
            onNotificationClick = onNotificationClick
        )

        // Promotion Card
        InfoContainer(
            isProminent = true,
            tagText = stringResource(R.string.loan_limited_time_offer),
            tagIcon = painterResource(id = R.drawable.ic_alarm),
            titleText = stringResource(R.string.loan_safe_secure_title),
            subtitleText = stringResource(R.string.loan_rayland_subtitle),
            imagePainter = painterResource(id = R.drawable.loan_info),
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 12.dp)
        )

        // Loan Limit Card
        InfoContainer(
            borrowLabel = stringResource(R.string.loan_borrow_up_to),
            borrowAmount = stringResource(R.string.loan_borrow_amount),
            evaluationLabel = stringResource(R.string.loan_subject_to_evaluation),
            detailsTitle = stringResource(R.string.loan_details_title),
            whatIsThisLabel = stringResource(R.string.loan_what_is_this),
            onWhatIsThisClick = { /* TODO: Navigate */ },
            details = listOf(
                LoanDetailData(
                    stringResource(R.string.loan_payable_in),
                    stringResource(R.string.loan_payable_value),
                    stringResource(R.string.loan_months)
                ),
                LoanDetailData(
                    stringResource(R.string.loan_interest_rate),
                    stringResource(R.string.loan_interest_value),
                    stringResource(R.string.loan_interest_label)
                ),
                LoanDetailData(
                    stringResource(R.string.loan_process_fee),
                    stringResource(R.string.loan_process_value),
                    stringResource(R.string.loan_process_label)
                )
            ),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
        )

        // How it works section
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = stringResource(R.string.loan_how_it_works_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(modifier = Modifier.fillMaxWidth()) {
                HIWCard(
                    title = stringResource(R.string.loan_how_credit_title),
                    description = stringResource(R.string.loan_how_credit_desc),
                    image = painterResource(id = R.drawable.loan_how_credit_score),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                HIWCard(
                    title = stringResource(R.string.loan_how_approval_title),
                    description = stringResource(R.string.loan_how_approval_desc),
                    image = painterResource(id = R.drawable.loan_how_approval),
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                HIWCard(
                    title = stringResource(R.string.loan_how_payments_title),
                    description = stringResource(R.string.loan_how_payments_desc),
                    image = painterResource(id = R.drawable.loan_how_easy_payments),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                HIWCard(
                    title = stringResource(R.string.loan_how_safe_title),
                    description = stringResource(R.string.loan_how_safe_desc),
                    image = painterResource(id = R.drawable.loan_how_safe_secure),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        AppButton(
            text = stringResource(R.string.loan_get_loan_button),
            onClick = onNavigateToApply,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )
    }
}
