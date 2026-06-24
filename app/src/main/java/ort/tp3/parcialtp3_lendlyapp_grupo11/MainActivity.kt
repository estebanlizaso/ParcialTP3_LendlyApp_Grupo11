package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation.AppNavigation
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin.CashInViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.RegisterViewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                // ViewModels que persisten durante la vida de la actividad o se comparten
                val registerViewModel: RegisterViewModel = hiltViewModel()
                val loanViewModel: LoanViewModel = hiltViewModel()
                val cashInViewModel: CashInViewModel = hiltViewModel()

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
