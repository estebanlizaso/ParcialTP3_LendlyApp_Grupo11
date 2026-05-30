package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppLabel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.CashInOptionsScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.HomeScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme

private enum class AppScreen {
    HOME,
    CASH_IN_OPTIONS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcialTP3_LendlyApp_Grupo11Theme {
                var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (currentScreen) {
                        AppScreen.HOME -> HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onCashInClick = { currentScreen = AppScreen.CASH_IN_OPTIONS }
                        )

                        AppScreen.CASH_IN_OPTIONS -> CashInOptionsScreen(
                            modifier = Modifier.padding(innerPadding),
                            onBackClick = { currentScreen = AppScreen.HOME }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    // Definimos el estado para los inputs
    var phoneNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+65") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ejemplo de Inputs alineados (como en tu imagen)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            AppLabel(
                label = "Phone Number",
                value = countryCode,
                onValueChange = { countryCode = it },
                modifier = Modifier.width(80.dp),
                placeholder = "+54"
            )
            AppLabel(
                label = "", // Sin label para que alinee con el anterior
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.weight(1f),
                placeholder = "11 2345 6789",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón que usa los datos ingresados
        AppButton(
            text = "Continuar",
            onClick = { 
                println("Iniciando sesión con: $countryCode $phoneNumber")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        LoginScreen()
    }
}
