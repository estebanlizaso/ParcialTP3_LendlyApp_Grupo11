package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.HomeRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.TransactionIconType
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    var uiState by mutableStateOf(TransactionDetailUiState())
        private set

    private var loadedTransactionId: String? = null

    fun loadTransaction(transactionId: String) {
        if (transactionId == loadedTransactionId && uiState.detail != null) return

        loadedTransactionId = transactionId
        uiState = TransactionDetailUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val transactions = repository.getTransactions().transactions
                val loans = repository.getLoans().loans
                val transaction = transactions.firstOrNull { it.id == transactionId }

                uiState = if (transaction != null) {
                    TransactionDetailUiState(
                        isLoading = false,
                        detail = transaction.toDetailUi(loans)
                    )
                } else {
                    TransactionDetailUiState(
                        isLoading = false,
                        error = "No se encontro la transaccion"
                    )
                }
            } catch (e: Exception) {
                uiState = TransactionDetailUiState(
                    isLoading = false,
                    error = "No se pudo cargar el detalle"
                )
            }
        }
    }

    private fun TransactionDto.toDetailUi(loans: List<LoanDto>): TransactionDetailUi {
        val relatedLoan = loans.firstOrNull { it.id == loanId }
        val merchant = relatedLoan?.lender ?: merchantFromTitle()

        return TransactionDetailUi(
            iconType = iconType(),
            title = titleForType(),
            amount = formatMoney(amount, currency),
            destination = destinationForType(merchant),
            category = categoryForType(),
            fee = "₱100.00",
            dateTime = formatDate(date),
            transactionNumber = "#$referenceNumber"
        )
    }

    private fun TransactionDto.iconType(): TransactionIconType {
        return when (type) {
            "LOAN_PAYMENT" -> TransactionIconType.PAYMENT
            "CASH_IN",
            "LOAN_DISBURSEMENT" -> TransactionIconType.ADD
            else -> if (amount < 0) TransactionIconType.PAYMENT else TransactionIconType.ADD
        }
    }

    private fun TransactionDto.titleForType(): String {
        return when (type) {
            "LOAN_PAYMENT" -> "Paid this month"
            "CASH_IN" -> "Added to your account"
            "LOAN_DISBURSEMENT" -> "Loan approved"
            else -> description.ifBlank { title }
        }
    }

    private fun TransactionDto.destinationForType(merchant: String): String {
        return when (type) {
            "LOAN_PAYMENT" -> "To $merchant"
            "CASH_IN" -> "From ${description.ifBlank { merchant }}"
            "LOAN_DISBURSEMENT" -> "From $merchant"
            else -> merchant
        }
    }

    private fun TransactionDto.categoryForType(): String {
        return when (type) {
            "LOAN_PAYMENT" -> "Paid Bills"
            "CASH_IN" -> "Cash-In"
            "LOAN_DISBURSEMENT" -> "Loan"
            else -> status.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    private fun TransactionDto.merchantFromTitle(): String {
        return title.substringAfter("\u2014", title).trim()
    }

    private fun formatMoney(value: Double, currency: String): String {
        return String.format(Locale.US, "%,.2f %s", abs(value), currency)
    }

    private fun formatDate(rawDate: String): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val formatter = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.US)
            formatter.format(parser.parse(rawDate) ?: return rawDate)
        } catch (e: Exception) {
            rawDate
        }
    }
}
