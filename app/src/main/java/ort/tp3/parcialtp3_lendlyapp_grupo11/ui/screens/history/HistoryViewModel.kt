package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    var uiState by mutableStateOf(sampleHistoryUiState())
        private set

    init {
        observeUser()
    }

    private fun observeUser() {
        val uid = sessionManager.getToken()
        if (uid != null) {
            viewModelScope.launch {
                userDao.getUserById(uid).collectLatest { user ->
                    user?.let {
                        uiState = uiState.copy(
                            avatarUrl = it.avatar
                        )
                    }
                }
            }
        }
    }
}
