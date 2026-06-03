package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.cashin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CashInViewModel : ViewModel() {
    var selectedSource by mutableStateOf("BPI")
}
