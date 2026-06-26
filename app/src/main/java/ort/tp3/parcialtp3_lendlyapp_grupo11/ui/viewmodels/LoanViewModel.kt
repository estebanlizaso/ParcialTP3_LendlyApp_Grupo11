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
import javax.inject.Inject

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
    private val firestoreRepository: ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository
) : ViewModel() {

    private val _loansState = MutableStateFlow<LoanUiState>(LoanUiState.Idle)
    val loansState: StateFlow<LoanUiState> = _loansState.asStateFlow()

    private val _applyState = MutableStateFlow<LoanApplyUiState>(LoanApplyUiState.Idle)
    val applyState: StateFlow<LoanApplyUiState> = _applyState.asStateFlow()

    private val _selectedLoanOption = MutableStateFlow<LoanOptionData?>(null)
    val selectedLoanOption: StateFlow<LoanOptionData?> = _selectedLoanOption.asStateFlow()

    private val _appliedAmount = MutableStateFlow<String>("")
    val appliedAmount: StateFlow<String> = _appliedAmount.asStateFlow()

    private val _avatarUrl = MutableStateFlow<String>("")
    val avatarUrl: StateFlow<String> = _avatarUrl.asStateFlow()

    private val _userScoring = MutableStateFlow<ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserScoring?>(null)
    val userScoring: StateFlow<ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserScoring?> = _userScoring.asStateFlow()

    init {
        observeUser()
        loadUserScoring()
    }

    private fun loadUserScoring() {
        val uid = sessionManager.getToken()
        if (uid != null) {
            viewModelScope.launch {
                val scoring = firestoreRepository.getUserScoring(uid)
                _userScoring.value = scoring
                
                // Sincronizamos Firestore -> Room para que el resto de la app vea el balance actualizado
                scoring?.let {
                    userDao.updateAccountBalance(uid, it.availableBalance)
                    userDao.updateCreditScore(uid, it.creditScore)
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
                // Obtenemos los préstamos reales desde Firestore
                val loans = firestoreRepository.getUserLoans(uid)
                
                // Construimos una respuesta compatible con el estado anterior
                val response = LoansResponse(
                    success = true,
                    loans = loans,
                    summary = ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansSummaryDto(
                        totalActive = loans.count { it.status.equals("ACTIVE", ignoreCase = true) },
                        totalPaid = loans.count { it.status.equals("PAID", ignoreCase = true) },
                        totalAmountDue = loans.sumOf { it.amountDue }
                    )
                )
                _loansState.value = LoanUiState.Success(response)
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
                scoring = firestoreRepository.getUserScoring(uid)
                _userScoring.value = scoring
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
            _appliedAmount.value = amount.toString()
            _selectedLoanOption.value = loanOption
            _applyState.value = LoanApplyUiState.Loading
            repository.applyLoan(LoanApplyRequest(amount, loanOption.title, purpose))
                .onSuccess {
                    if (it.success) {
                        // Al ser exitoso el préstamo en la API, actualizamos el saldo en Firestore y Room
                        viewModelScope.launch {
                            val uid = sessionManager.getToken()
                            if (uid != null) {
                                val user = userDao.getUserById(uid).firstOrNull()
                                val currentBalance = user?.accountBalance ?: 0.0
                                val newBalance = currentBalance + amount
                                
                                // Actualizamos ambas fuentes de verdad
                                firestoreRepository.updateBalance(uid, newBalance)
                                userDao.updateAccountBalance(uid, newBalance)

                                // Guardamos el nuevo préstamo en la sub-colección de Firestore
                                val totalMonths = loanOption.config?.months ?: 6
                                val interestRate = loanOption.config?.interestRate ?: 2.99
                                val installmentAmount = (amount * (1 + interestRate / 100)) / totalMonths
                                
                                val newLoan = ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanDto(
                                    id = "", // Firestore genera el ID
                                    lender = "Apple Inc.",
                                    lenderLogo = "https://logo.clearbit.com/apple.com",
                                    amount = amount,
                                    amountDue = amount * (1 + interestRate / 100),
                                    installmentAmount = installmentAmount,
                                    installmentPlan = loanOption.title,
                                    interestRate = interestRate,
                                    purpose = purpose,
                                    status = "ACTIVE",
                                    nextPaymentDate = "2024-08-15",
                                    nextPaymentLabel = "Next payment in August",
                                    startDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date()),
                                    endDate = "2025-01-15",
                                    paidInstallments = 0,
                                    totalInstallments = totalMonths
                                )
                                firestoreRepository.saveLoan(uid, newLoan)
                            }
                            _applyState.value = LoanApplyUiState.Success(it)
                        }
                    } else {
                        _applyState.value = LoanApplyUiState.Error(it.message)
                    }
                }
                .onFailure {
                    _applyState.value = LoanApplyUiState.Error(it.message ?: "Unknown error")
                }
        }
    }
    
    fun resetApplyState() {
        _applyState.value = LoanApplyUiState.Idle
    }
}
