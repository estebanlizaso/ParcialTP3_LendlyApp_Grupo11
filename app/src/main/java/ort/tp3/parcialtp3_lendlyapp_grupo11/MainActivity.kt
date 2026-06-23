package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.AppNavigation
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.RegisterViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                val context = LocalContext.current
                val sessionManager = remember { SessionManager(context) }
                
                // ViewModels que persisten durante la vida de la actividad o se comparten
                val registerViewModel = remember { RegisterViewModel(sessionManager = sessionManager) }
                val loanViewModel: LoanViewModel = viewModel()
                val cashInViewModel: CashInViewModel = viewModel()

                AppNavigation(
                    sessionManager = sessionManager,
                    registerViewModel = registerViewModel,
                    loanViewModel = loanViewModel,
                    cashInViewModel = cashInViewModel
                )
            }
        }
    }
}
