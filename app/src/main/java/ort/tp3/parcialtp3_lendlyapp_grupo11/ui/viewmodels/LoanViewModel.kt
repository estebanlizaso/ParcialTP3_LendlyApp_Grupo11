package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyRequest
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.LoanRepository
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.loan.LoanOptionData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanBusinessRules
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansSummaryDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserScoring
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository

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
        val uid = sessionManager.getToken()
        if (uid != null) {
            viewModelScope.launch {
                try {
                    val scoring = firestoreRepository.getUserScoring(uid)
                    _userScoring.value = scoring
                    
                    // Sincronizamos Firestore -> Room para que el resto de la app vea el balance actualizado
                    scoring?.let {
                        userDao.updateAccountBalance(uid, it.availableBalance)
                        userDao.updateCreditScore(uid, it.creditScore)
                    }
                } catch (e: Exception) {
                    // Si falla la carga inicial de scoring, notificamos a través del estado de préstamos
                    _loansState.value = LoanUiState.Error("Failed to load user data: ${e.message}")
                }
            }
        }
    }

    private fun observeUser() {
        val uid = sessionManager.getToken()
        if (uid != null) {
            viewModelScope.launch {
                userDao.getUserById(uid).collectLatest { user ->
                    user?.let {
                        _avatarUrl.value = it.avatar
                    }
                }
            }
        }
    }

    fun fetchLoans() {
        viewModelScope.launch {
            _loansState.value = LoanUiState.Loading
            
            val uid = sessionManager.getToken()
            if (uid != null) {
                try {
                    // Obtenemos los préstamos reales desde Firestore
                    val loans = firestoreRepository.getUserLoans(uid)
                    
                    // Construimos una respuesta compatible con el estado anterior
                    val response = LoansResponse(
                        success = true,
                        loans = loans,
                        summary = LoansSummaryDto(
                            totalActive = loans.count { it.status.equals("ACTIVE", ignoreCase = true) },
                            totalPaid = loans.count { it.status.equals("PAID", ignoreCase = true) },
                            totalAmountDue = loans.sumOf { it.amountDue }
                        )
                    )
                    _loansState.value = LoanUiState.Success(response)
                } catch (e: Exception) {
                    _loansState.value = LoanUiState.Error("Failed to fetch loans: ${e.message}")
                }
            } else {
                _loansState.value = LoanUiState.Error("Session not found")
            }
        }
    }

    fun validateLoanRequest(requestedAmount: Double) {
        viewModelScope.launch {
            _applyState.value = LoanApplyUiState.Loading
            
            val uid = sessionManager.getToken()
            var scoring = _userScoring.value
            
            // Si no tenemos los datos en memoria, intentamos cargarlos de nuevo (Re-intento)
            if (scoring == null && uid != null) {
                try {
                    scoring = firestoreRepository.getUserScoring(uid)
                    _userScoring.value = scoring
                } catch (e: Exception) {
                    _applyState.value = LoanApplyUiState.Error("Failed to sync scoring data: ${e.message}")
                    return@launch
                }
            }
            
            // Si después del re-intento sigue siendo null, mostramos el error
            if (scoring == null) {
                _applyState.value = LoanApplyUiState.Error("User scoring data not available. Check your connection.")
                return@launch
            }

            // Validación de elegibilidad según Firestore
            if (!scoring.eligible) {
                _applyState.value = LoanApplyUiState.Error("You are not eligible for a loan at this time based on your credit score")
                return@launch
            }

            // Validación de cantidad de préstamos activos (máximo 5)
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

            // Validación del monto máximo permitido por Firestore
            if (requestedAmount > 0 && requestedAmount > scoring.loanLimit) {
                _applyState.value = LoanApplyUiState.Error("Amount exceeds your maximum allowed limit of ${String.format(Locale.US, "₱%,.2f", scoring.loanLimit)}")
                return@launch
            }

            _applyState.value = LoanApplyUiState.ValidationSuccess
        }
    }

    fun applyLoan(amount: Double, loanOption: LoanOptionData, purpose: String) {
        viewModelScope.launch {
            val lenderConfig = LoanBusinessRules.lendersByPurpose[purpose]
            val lenderName = lenderConfig?.name ?: "Rayland Partners"
            
            // Configuramos la zona horaria de Buenos Aires
            val now = Date()
            val argTimeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires")
            
            val dateTimeFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US)
            dateTimeFormat.timeZone = argTimeZone
            val formattedDate = dateTimeFormat.format(now)
            
            // Generamos un número de transacción aleatorio de 12 dígitos
            val transactionNumber = (1..12).map { (0..9).random() }.joinToString("", prefix = "#")
            
            // Formateamos el monto para que se vea con comas y 2 decimales en la pantalla de éxito
            _appliedAmount.value = String.format(Locale.US, "%,.2f", amount)
            _selectedLoanOption.value = loanOption
            _selectedLenderName.value = lenderName
            _appliedDateTime.value = formattedDate
            _appliedTransactionNumber.value = transactionNumber
            
            _applyState.value = LoanApplyUiState.Loading
            repository.applyLoan(LoanApplyRequest(amount, loanOption.title, purpose))
                .onSuccess { response ->
                    if (response.success) {
                        // Al ser exitoso el préstamo en la API, actualizamos el saldo en Firestore y Room
                        viewModelScope.launch {
                            try {
                                val uid = sessionManager.getToken()
                                if (uid != null) {
                                    val user = userDao.getUserById(uid).firstOrNull()
                                    val currentBalance = user?.accountBalance ?: 0.0
                                    val newBalance = currentBalance + amount
                                    
                                    // Actualizamos ambas fuentes de verdad
                                    val scoring = _userScoring.value
                                    if (scoring != null) {
                                        val updatedScoring = scoring.copy(
                                            availableBalance = newBalance,
                                            loanLimit = 15000.0 * (scoring.creditScore.toDouble() / 100.0)
                                        )
                                        firestoreRepository.updateScoring(uid, updatedScoring)
                                    } else {
                                        firestoreRepository.updateBalance(uid, newBalance)
                                    }
                                    userDao.updateAccountBalance(uid, newBalance)

                                    // Guardamos el nuevo préstamo en la sub-colección de Firestore
                                    val totalMonths = loanOption.config?.months ?: 6
                                    val interestRate = loanOption.config?.interestRate ?: 2.99
                                    val installmentAmount = (amount * (1 + interestRate / 100)) / totalMonths
                                    
                                    val startDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                    startDateFormat.timeZone = argTimeZone
                                    val startDateStr = startDateFormat.format(now)
                                    
                                    val nextPaymentLabel = calculateNextPaymentLabel(startDateStr, 0)
                                    
                                    // Calcular nextPaymentDate (1 mes después de startDate)
                                    val calendar = Calendar.getInstance(argTimeZone)
                                    calendar.time = now
                                    calendar.add(Calendar.MONTH, 1)
                                    val nextPaymentDateStr = startDateFormat.format(calendar.time)

                                    val newLoan = LoanDto(
                                        id = response.loan?.id ?: "", 
                                        lender = lenderName, // Usamos el nombre calculado al inicio
                                        lenderLogo = lenderConfig?.logo ?: "https://favicon.im/apple.com?larger=true",
                                        amount = amount,
                                        amountDue = amount * (1 + interestRate / 100),
                                        installmentAmount = installmentAmount,
                                        installmentPlan = loanOption.title,
                                        interestRate = interestRate,
                                        purpose = purpose,
                                        status = "ACTIVE",
                                        nextPaymentDate = nextPaymentDateStr,
                                        nextPaymentLabel = nextPaymentLabel,
                                        startDate = startDateStr,
                                        endDate = "2025-01-15",
                                        paidInstallments = 0,
                                        totalInstallments = totalMonths,
                                        transactionNumber = transactionNumber
                                    )
                                    firestoreRepository.saveLoan(uid, newLoan)
                                }
                                _applyState.value = LoanApplyUiState.Success(response)
                            } catch (e: Exception) {
                                _applyState.value = LoanApplyUiState.Error("Loan approved but failed to sync with database: ${e.message}")
                            }
                        }
                    } else {
                        _applyState.value = LoanApplyUiState.Error(response.message)
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
        _selectedLoanForPayment.value = loan
        
        // Preparar detalles de la transacción
        val now = Date()
        val argTimeZone = TimeZone.getTimeZone("America/Argentina/Buenos_Aires")
        val dateTimeFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US)
        dateTimeFormat.timeZone = argTimeZone
        
        _appliedDateTime.value = dateTimeFormat.format(now)
        _appliedTransactionNumber.value = (1..12).map { (0..9).random() }.joinToString("", prefix = "#")
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
                
                // 1. Actualizar balance y scoring
                val newBalance = scoring.availableBalance - loan.installmentAmount
                val updatedScoring = scoring.copy(
                    availableBalance = newBalance,
                    loanLimit = 15000.0 * (scoring.creditScore.toDouble() / 100.0)
                )
                
                firestoreRepository.updateScoring(uid, updatedScoring)
                userDao.updateAccountBalance(uid, newBalance)
                
                // 2. Actualizar préstamo
                val newPaidInstallments = loan.paidInstallments + 1
                val newStatus = if (newPaidInstallments >= loan.totalInstallments) "PAID" else "ACTIVE"
                
                val nextPaymentLabel = if (newStatus == "PAID") null else {
                    calculateNextPaymentLabel(loan.startDate, newPaidInstallments)
                }
                
                val nextPaymentDate = if (newStatus == "PAID") null else {
                    try {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        val date = sdf.parse(loan.startDate)
                        val calendar = Calendar.getInstance()
                        calendar.time = date!!
                        calendar.add(Calendar.MONTH, newPaidInstallments + 1)
                        sdf.format(calendar.time)
                    } catch (e: Exception) {
                        null
                    }
                }
                
                val updatedLoan = loan.copy(
                    paidInstallments = newPaidInstallments,
                    status = newStatus,
                    nextPaymentDate = nextPaymentDate,
                    nextPaymentLabel = nextPaymentLabel,
                    transactionNumber = _appliedTransactionNumber.value
                )
                
                firestoreRepository.updateLoan(uid, updatedLoan)
                
                // 3. Recargar scoring local y préstamos
                loadUserScoring()
                fetchLoans()
                
                _paymentState.value = LoanApplyUiState.Success(LoanApplyResponse(success = true, message = "Payment successful", loan = null))
            } catch (e: Exception) {
                _paymentState.value = LoanApplyUiState.Error("Payment failed due to a database error: ${e.message}")
            }
        }
    }

    private fun calculateNextPaymentLabel(startDate: String, paidInstallments: Int): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = sdf.parse(startDate) ?: return "Pending fee"
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.MONTH, paidInstallments + 1)

            val monthFormat = SimpleDateFormat("MMMM", Locale.US)
            val monthName = monthFormat.format(calendar.time)
            "Fee of ${monthName.replaceFirstChar { it.uppercase() }}"
        } catch (e: Exception) {
            "Pending fee"
        }
    }
}
