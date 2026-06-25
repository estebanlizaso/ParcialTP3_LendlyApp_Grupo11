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
    private val sessionManager: SessionManager
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

    init {
        observeUser()
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
            repository.getLoans()
                .onSuccess {
                    if (it.success) {
                        _loansState.value = LoanUiState.Success(it)
                    } else {
                        _loansState.value = LoanUiState.Error("Failed to fetch loans")
                    }
                }
                .onFailure {
                    _loansState.value = LoanUiState.Error(it.message ?: "Unknown error")
                }
        }
    }

    fun validateLoanRequest(requestedAmount: Double) {
        viewModelScope.launch {
            val uid = sessionManager.getToken()
            if (uid == null) {
                _applyState.value = LoanApplyUiState.Error("Session not found")
                return@launch
            }

            val user = userDao.getUserById(uid).firstOrNull()
            if (user == null) {
                _applyState.value = LoanApplyUiState.Error("User data not found")
                return@launch
            }

            if (user.creditScore < 500) {
                _applyState.value = LoanApplyUiState.Error("Credit score too low for loans")
                return@launch
            }

            val maxAllowed = if (user.creditScore <= 700) {
                user.accountBalance * 1.5
            } else {
                user.accountBalance * 3.0
            }

            if (requestedAmount > maxAllowed) {
                _applyState.value = LoanApplyUiState.Error("Amount exceeds your maximum allowed limit of $${String.format(Locale.US, "%.2f", maxAllowed)}")
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
                        _applyState.value = LoanApplyUiState.Success(it)
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
