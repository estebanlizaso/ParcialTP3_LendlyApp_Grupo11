package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login.CreatePasswordPage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CreatePasswordPage(
                        modifier = Modifier.padding(innerPadding),
                        onNextClick = {
                            println("¡REGISTRO EXITOSO! La API devolvió el token y deberíamos ir a la DonePage.")
                        },
                        onBackClick = { }
                    )
                }
            }
        }
    }
}