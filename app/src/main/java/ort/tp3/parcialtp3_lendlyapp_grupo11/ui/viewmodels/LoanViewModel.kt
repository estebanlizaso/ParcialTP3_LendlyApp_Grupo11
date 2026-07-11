package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.FirestoreTransaction
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyRequest
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanBusinessRules
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansSummaryDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserScoring
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.LoanRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.LoanOptionData

sealed class LoanUiState {
    object Idle : LoanUiState()
    object Loading : LoanUiState()
    data class Success(val data: LoansResponse) : LoanUiState()
    data class Error(val message: String) : LoanUiState()
}

sealed class LoanApplyUiState {
    object Idle : LoanApplyUiState()
    object Loading : LoanApplyUiState()
    object ValidationSuccess : LoanApplyUiState()
    data class Success(val response: LoanApplyResponse) : LoanApplyUiState()
    data class Error(val message: String) : LoanApplyUiState()
}

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val repository: LoanRepository,
    private val userDao: UserDao,
    private val sessionManager: SessionManager,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _loansState = MutableStateFlow<LoanUiState>(LoanUiState.Idle)
    val loansState: StateFlow<LoanUiState> = _loansState.asStateFlow()

    private val _applyState = MutableStateFlow<LoanApplyUiState>(LoanApplyUiState.Idle)
    val applyState: StateFlow<LoanApplyUiState> = _applyState.asStateFlow()

    private val _selectedLoanOption = MutableStateFlow<LoanOptionData?>(null)
    val selectedLoanOption: StateFlow<LoanOptionData?> = _selectedLoanOption.asStateFlow()

    private val _appliedAmount = MutableStateFlow("")
    val appliedAmount: StateFlow<String> = _appliedAmount.asStateFlow()

    private val _selectedLenderName = MutableStateFlow("")
    val selectedLenderName: StateFlow<String> = _selectedLenderName.asStateFlow()

    private val _appliedDateTime = MutableStateFlow("")
    val appliedDateTime: StateFlow<String> = _appliedDateTime.asStateFlow()

    private val _appliedTransactionNumber = MutableStateFlow("")
    val appliedTransactionNumber: StateFlow<String> = _appliedTransactionNumber.asStateFlow()

    private val _avatarUrl = MutableStateFlow("")
    val avatarUrl: StateFlow<String> = _avatarUrl.asStateFlow()

    private val _userScoring = MutableStateFlow<UserScoring?>(null)
    val userScoring: StateFlow<UserScoring?> = _userScoring.asStateFlow()

    private val _selectedLoanForPayment = MutableStateFlow<LoanDto?>(null)
    val selectedLoanForPayment: StateFlow<LoanDto?> = _selectedLoanForPayment.asStateFlow()

    private val _paymentState = MutableStateFlow<LoanApplyUiState>(LoanApplyUiState.Idle)
    val paymentState: StateFlow<LoanApplyUiState> = _paymentState.asStateFlow()

    init {
        observeUser()
        loadUserScoring()
    }

    private fun loadUserScoring() {
        val uid = sessionManager.getToken() ?: return

        viewModelScope.launch {
            try {
                val scoring = firestoreRepository.getUserScoring(uid)
                _userScoring.value = scoring

                scoring?.let {
                    userDao.updateAccountBalance(uid, it.availableBalance)
                    userDao.updateCreditScore(uid, it.creditScore)
                }
            } catch (e: Exception) {
                _loansState.value = LoanUiState.Error("Failed to load user data: ${e.message}")
            }
        }
    }

    private fun observeUser() {
        val uid = sessionManager.getToken() ?: return

        viewModelScope.launch {
            userDao.getUserById(uid).collectLatest { user ->
                user?.let { _avatarUrl.value = it.avatar }
            }
        }
    }

    fun fetchLoans() {
        viewModelScope.launch {
            _loansState.value = LoanUiState.Loading

            val uid = sessionManager.getToken()
            if (uid == null) {
                _loansState.value = LoanUiState.Error("Session not found")
                return@launch
            }

            try {
                val loans = firestoreRepository.getUserLoans(uid)
                _loansState.value = LoanUiState.Success(loans.toLoansResponse())
            } catch (e: Exception) {
                _loansState.value = LoanUiState.Error("Failed to fetch loans: ${e.message}")
            }
        }
    }

    fun validateLoanRequest(requestedAmount: Double) {
        viewModelScope.launch {
            _applyState.value = LoanApplyUiState.Loading

            val uid = sessionManager.getToken()
            var scoring = _userScoring.value

            if (scoring == null && uid != null) {
                try {
                    scoring = firestoreRepository.getUserScoring(uid)
                    _userScoring.value = scoring
                } catch (e: Exception) {
                    _applyState.value = LoanApplyUiState.Error("Failed to sync scoring data: ${e.message}")
                    return@launch
                }
            }

            if (scoring == null) {
                _applyState.value = LoanApplyUiState.Error("User scoring data not available. Check your connection.")
                return@launch
            }

            if (!scoring.eligible) {
                _applyState.value = LoanApplyUiState.Error("You are not eligible for a loan at this time based on your credit score")
                return@launch
            }

            if (uid != null) {
                try {
                    val activeLoans = firestoreRepository.getUserLoans(uid).count { it.status.equals("ACTIVE", ignoreCase = true) }
                    if (activeLoans >= 5) {
                        _applyState.value = LoanApplyUiState.Error("You have reached the limit of 5 active loans. Please pay one to apply for a new one.")
                        return@launch
                    }
                } catch (e: Exception) {
                    _applyState.value = LoanApplyUiState.Error("Failed to verify active loans: ${e.message}")
                    return@launch
                }
            }

            if (requestedAmount > 0 && requestedAmount > scoring.loanLimit) {
                _applyState.value = LoanApplyUiState.Error("Amount exceeds your maximum allowed limit of ${formatCurrency(scoring.loanLimit)}")
                return@launch
            }

            _applyState.value = LoanApplyUiState.ValidationSuccess
        }
    }

    fun applyLoan(amount: Double, loanOption: LoanOptionData, purpose: String) {
        viewModelScope.launch {
            val lenderConfig = LoanBusinessRules.lendersByPurpose[purpose]
            val lenderName = lenderConfig?.name ?: "Rayland Partners"
            val now = Date()
            val appTimeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires")
            val startDate = dateFormatter("yyyy-MM-dd", appTimeZone).format(now)
            val transactionNumber = generateReferenceNumber(now)

            _appliedAmount.value = String.format(Locale.US, "%,.2f", amount)
            _selectedLoanOption.value = loanOption
            _selectedLenderName.value = lenderName
            _appliedDateTime.value = dateFormatter("MMM dd, yyyy h:mm a", appTimeZone).format(now)
            _appliedTransactionNumber.value = transactionNumber
            _applyState.value = LoanApplyUiState.Loading

            repository.applyLoan(LoanApplyRequest(amount, loanOption.title, purpose))
                .onSuccess { response ->
                    if (!response.success) {
                        _applyState.value = LoanApplyUiState.Error(response.message)
                        return@onSuccess
                    }

                    try {
                        val uid = sessionManager.getToken() ?: throw IllegalStateException("Session not found")
                        val user = userDao.getUserById(uid).firstOrNull()
                        val currentBalance = _userScoring.value?.availableBalance ?: user?.accountBalance ?: 0.0
                        val newBalance = currentBalance + amount

                        val scoring = _userScoring.value
                        if (scoring != null) {
                            val updatedScoring = scoring.copy(
                                availableBalance = newBalance,
                                loanLimit = calculateLoanLimit(scoring.creditScore)
                            )
                            firestoreRepository.updateScoring(uid, updatedScoring)
                            _userScoring.value = updatedScoring
                        } else {
                            firestoreRepository.updateBalance(uid, newBalance)
                        }
                        userDao.updateAccountBalance(uid, newBalance)

                        val totalMonths = loanOption.config?.months ?: 6
                        val interestRate = loanOption.config?.interestRate ?: 2.99
                        val installmentAmount = (amount * (1 + interestRate / 100)) / totalMonths
                        val nextPaymentDate = nextPaymentDate(now, appTimeZone)

                        val newLoan = LoanDto(
                            id = response.loan?.id.orEmpty(),
                            lender = lenderName,
                            lenderLogo = lenderConfig?.logo ?: "https://favicon.im/apple.com?larger=true",
                            amount = amount,
                            amountDue = amount * (1 + interestRate / 100),
                            installmentAmount = installmentAmount,
                            installmentPlan = loanOption.title,
                            interestRate = interestRate,
                            purpose = purpose,
                            status = "ACTIVE",
                            nextPaymentDate = nextPaymentDate,
                            nextPaymentLabel = calculateNextPaymentLabel(startDate, 0),
                            startDate = startDate,
                            endDate = calculateEndDate(now, totalMonths, appTimeZone),
                            paidInstallments = 0,
                            totalInstallments = totalMonths,
                            transactionNumber = transactionNumber
                        )

                        val loanId = firestoreRepository.saveLoan(uid, newLoan)
                        if (loanId != null) {
                            firestoreRepository.saveTransaction(
                                uid = uid,
                                transaction = FirestoreTransaction(
                                    type = "LOAN_DISBURSEMENT",
                                    title = "Loan approved",
                                    description = "$purpose - $lenderName",
                                    amount = amount,
                                    currency = "PHP",
                                    status = "COMPLETED",
                                    date = formatTransactionDate(now),
                                    dateKey = startDate,
                                    loanId = loanId,
                                    sourceName = lenderName,
                                    referenceNumber = transactionNumber
                                )
                            )
                        }

                        fetchLoans()
                        _applyState.value = LoanApplyUiState.Success(response)
                    } catch (e: Exception) {
                        _applyState.value = LoanApplyUiState.Error("Loan approved but failed to sync with database: ${e.message}")
                    }
                }
                .onFailure {
                    _applyState.value = LoanApplyUiState.Error(it.message ?: "Unknown error during application")
                }
        }
    }

    fun resetApplyState() {
        _applyState.value = LoanApplyUiState.Idle
        _paymentState.value = LoanApplyUiState.Idle
    }

    fun selectLoanForPayment(loan: LoanDto) {
        val now = Date()
        val appTimeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires")

        _selectedLoanForPayment.value = loan
        _appliedDateTime.value = dateFormatter("MMM dd, yyyy h:mm a", appTimeZone).format(now)
        _appliedTransactionNumber.value = generateReferenceNumber(now)
        _paymentState.value = LoanApplyUiState.Idle
    }

    fun confirmPayment() {
        val loan = _selectedLoanForPayment.value ?: return
        val uid = sessionManager.getToken() ?: return

        viewModelScope.launch {
            _paymentState.value = LoanApplyUiState.Loading

            try {
                val scoring = firestoreRepository.getUserScoring(uid)
                if (scoring == null) {
                    _paymentState.value = LoanApplyUiState.Error("User scoring data not found. Check your connection.")
                    return@launch
                }

                if (scoring.availableBalance < loan.installmentAmount) {
                    _paymentState.value = LoanApplyUiState.Error("Insufficient balance to pay this installment")
                    return@launch
                }

                val newBalance = scoring.availableBalance - loan.installmentAmount
                val updatedScoring = scoring.copy(
                    availableBalance = newBalance,
                    loanLimit = calculateLoanLimit(scoring.creditScore)
                )

                firestoreRepository.updateScoring(uid, updatedScoring)
                userDao.updateAccountBalance(uid, newBalance)
                _userScoring.value = updatedScoring

                val newPaidInstallments = loan.paidInstallments + 1
                val newStatus = if (newPaidInstallments >= loan.totalInstallments) "PAID" else "ACTIVE"
                val updatedLoan = loan.copy(
                    paidInstallments = newPaidInstallments,
                    status = newStatus,
                    nextPaymentDate = if (newStatus == "PAID") null else calculateNextPaymentDate(loan.startDate, newPaidInstallments),
                    nextPaymentLabel = if (newStatus == "PAID") null else calculateNextPaymentLabel(loan.startDate, newPaidInstallments),
                    transactionNumber = _appliedTransactionNumber.value
                )

                firestoreRepository.updateLoan(uid, updatedLoan)
                firestoreRepository.saveTransaction(
                    uid = uid,
                    transaction = FirestoreTransaction(
                        type = "LOAN_PAYMENT",
                        title = "Paid this month",
                        description = "${loan.purpose} - ${loan.lender}",
                        amount = loan.installmentAmount,
                        currency = "PHP",
                        status = "COMPLETED",
                        date = formatTransactionDate(Date()),
                        dateKey = formatDateKey(Date()),
                        loanId = loan.id,
                        sourceName = loan.lender,
                        referenceNumber = _appliedTransactionNumber.value
                    )
                )

                fetchLoans()
                _paymentState.value = LoanApplyUiState.Success(
                    LoanApplyResponse(success = true, message = "Payment successful", loan = null)
                )
            } catch (e: Exception) {
                _paymentState.value = LoanApplyUiState.Error("Payment failed due to a database error: ${e.message}")
            }
        }
    }

    private fun List<LoanDto>.toLoansResponse(): LoansResponse {
        return LoansResponse(
            success = true,
            loans = this,
            summary = LoansSummaryDto(
                totalActive = count { it.status.equals("ACTIVE", ignoreCase = true) },
                totalPaid = count { it.status.equals("PAID", ignoreCase = true) },
                totalAmountDue = sumOf { it.amountDue }
            )
        )
    }

    private fun calculateNextPaymentLabel(startDate: String, paidInstallments: Int): String {
        return try {
            val date = dateFormatter("yyyy-MM-dd", TimeZone.getDefault()).parse(startDate) ?: return "Pending fee"
            val calendar = Calendar.getInstance().apply { time = date }
            calendar.add(Calendar.MONTH, paidInstallments + 1)

            val monthName = SimpleDateFormat("MMMM", Locale.US).format(calendar.time)
            "Fee of ${monthName.replaceFirstChar { it.uppercase() }}"
        } catch (e: Exception) {
            "Pending fee"
        }
    }

    private fun calculateNextPaymentDate(startDate: String, paidInstallments: Int): String? {
        return try {
            val sdf = dateFormatter("yyyy-MM-dd", TimeZone.getDefault())
            val date = sdf.parse(startDate) ?: return null
            val calendar = Calendar.getInstance().apply { time = date }
            calendar.add(Calendar.MONTH, paidInstallments + 1)
            sdf.format(calendar.time)
        } catch (e: Exception) {
            null
        }
    }

    private fun nextPaymentDate(date: Date, timeZone: TimeZone): String {
        val calendar = Calendar.getInstance(timeZone).apply { time = date }
        calendar.add(Calendar.MONTH, 1)
        return dateFormatter("yyyy-MM-dd", timeZone).format(calendar.time)
    }

    private fun calculateEndDate(date: Date, months: Int, timeZone: TimeZone): String {
        val calendar = Calendar.getInstance(timeZone).apply { time = date }
        calendar.add(Calendar.MONTH, months)
        return dateFormatter("yyyy-MM-dd", timeZone).format(calendar.time)
    }

    private fun calculateLoanLimit(creditScore: Int): Double {
        return 15000.0 * (creditScore.toDouble() / 100.0)
    }

    private fun formatCurrency(value: Double): String {
        return String.format(Locale.US, "₱%,.2f", value)
    }

    private fun formatTransactionDate(date: Date): String {
        return utcFormatter("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date)
    }

    private fun formatDateKey(date: Date): String {
        return utcFormatter("yyyy-MM-dd").format(date)
    }

    private fun generateReferenceNumber(date: Date): String {
        return "#${date.time}"
    }

    private fun utcFormatter(pattern: String): SimpleDateFormat {
        return dateFormatter(pattern, TimeZone.getTimeZone("UTC"))
    }

    private fun dateFormatter(pattern: String, timeZone: TimeZone): SimpleDateFormat {
        return SimpleDateFormat(pattern, Locale.US).apply { this.timeZone = timeZone }
    }
}
