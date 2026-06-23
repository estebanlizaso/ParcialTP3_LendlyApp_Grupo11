package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository

class HistoryViewModel(
    private val repository: HomeRepository = HomeRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState(isLoading = true)

            try {
                val userResponse = repository.getUser()
                val transactionsResponse = repository.getTransactions()
                val loansResponse = repository.getLoans()
                val loans = loansResponse.loans

                _uiState.value = HistoryUiState(
                    isLoading = false,
                    avatarUrl = userResponse.user.avatar,
                    todayTransactions = transactionsResponse.transactions.map { transaction ->
                        transaction.toHistoryTransactionUi(loans)
                    },
                    recentLoans = loans.map { loan -> loan.toRecentLoanHistoryUi() }
                )
            } catch (e: Exception) {
                _uiState.value = HistoryUiState(
                    isLoading = false,
                    error = "No se pudo cargar el historial"
                )
            }
        }
    }

    private fun TransactionDto.toHistoryTransactionUi(loans: List<LoanDto>): HistoryTransactionUi {
        val relatedLoan = loans.firstOrNull { it.id == loanId }
        val merchant = relatedLoan?.lender ?: merchantFromTitle()

        return HistoryTransactionUi(
            id = id,
            time = formatTransactionTime(date),
            title = titleForType(),
            merchant = merchant,
            amount = formatMoney(amount, currency),
            type = type.toHistoryTransactionType(amount)
        )
    }

    private fun LoanDto.toRecentLoanHistoryUi(): RecentLoanHistoryUi {
        return RecentLoanHistoryUi(
            date = formatLoanDate(startDate),
            productName = purpose,
            merchant = lender,
            status = status.lowercase().replaceFirstChar { it.uppercase() }
        )
    }

    private fun TransactionDto.titleForType(): String {
        return when (type) {
            "LOAN_PAYMENT" -> "Paid this month"
            "CASH_IN" -> "Added"
            "LOAN_DISBURSEMENT" -> "Loan approved"
            else -> description.ifBlank { title }
        }
    }

    private fun TransactionDto.merchantFromTitle(): String {
        return title.substringAfter("\u2014", "").trim()
    }

    private fun String.toHistoryTransactionType(amount: Double): HistoryTransactionType {
        return when (this) {
            "LOAN_PAYMENT" -> HistoryTransactionType.PAID_BILL
            "CASH_IN",
            "LOAN_DISBURSEMENT" -> HistoryTransactionType.ADDED_BALANCE
            else -> if (amount < 0) HistoryTransactionType.PAID_BILL else HistoryTransactionType.ADDED_BALANCE
        }
    }

    private fun formatMoney(value: Double, currency: String): String {
        return String.format(Locale.US, "%,.2f %s", abs(value), currency)
    }

    private fun formatTransactionTime(rawDate: String): String {
        return formatDate(rawDate, "yyyy-MM-dd'T'HH:mm:ss'Z'", "h:mm a", useUtc = true)
    }

    private fun formatLoanDate(rawDate: String): String {
        return formatDate(rawDate, "yyyy-MM-dd", "MM/dd/yyyy", useUtc = false)
    }

    private fun formatDate(
        rawDate: String,
        inputPattern: String,
        outputPattern: String,
        useUtc: Boolean
    ): String {
        return try {
            val parser = SimpleDateFormat(inputPattern, Locale.US).apply {
                if (useUtc) timeZone = TimeZone.getTimeZone("UTC")
            }
            val formatter = SimpleDateFormat(outputPattern, Locale.US)
            formatter.format(parser.parse(rawDate) ?: return rawDate)
        } catch (e: Exception) {
            rawDate
        }
    }
}
