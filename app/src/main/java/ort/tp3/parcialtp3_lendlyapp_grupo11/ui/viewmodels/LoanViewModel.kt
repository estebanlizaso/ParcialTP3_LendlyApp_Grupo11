package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyRequest
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoanApplyResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
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
    data class Success(val response: LoanApplyResponse) : LoanApplyUiState()
    data class Error(val message: String) : LoanApplyUiState()
}

class LoanViewModel(
    private val repository: LoanRepository = LoanRepository()
) : ViewModel() {

    private val _loansState = MutableStateFlow<LoanUiState>(LoanUiState.Idle)
    val loansState: StateFlow<LoanUiState> = _loansState.asStateFlow()

    private val _applyState = MutableStateFlow<LoanApplyUiState>(LoanApplyUiState.Idle)
    val applyState: StateFlow<LoanApplyUiState> = _applyState.asStateFlow()

    private val _selectedLoanOption = MutableStateFlow<LoanOptionData?>(null)
    val selectedLoanOption: StateFlow<LoanOptionData?> = _selectedLoanOption.asStateFlow()

    private val _appliedAmount = MutableStateFlow<String>("")
    val appliedAmount: StateFlow<String> = _appliedAmount.asStateFlow()

    fun fetchLoans() {
        viewModelScope.launch {
            _loansState.value = LoanUiState.Loading
            repository.getLoans()
                .onSuccess {
                    _loansState.value = LoanUiState.Success(it)
                }
                .onFailure {
                    _loansState.value = LoanUiState.Error(it.message ?: "Unknown error")
                }
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
