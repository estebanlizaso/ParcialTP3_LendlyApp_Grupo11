package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyPhoneNumber(
    onBackClick: () -> Unit,
    onSendCodeClick: () -> Unit
) {
    var countryCode by remember { mutableStateOf("+65") }
    var phoneNumber by remember { mutableStateOf("991251255") }


    // fuentes
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(
                onLeftClick = onBackClick,
                rightIcon = { InfoIcon(onClick = { /* Mostrar info */ }) }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))


        Column(modifier = Modifier.padding(horizontal = 24.dp)) {

            Text(
                text = "Verify your phone\nnumber with a code",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E),
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We will send you a One-Time-Password\n(OTP) to confirm you number.",
                fontFamily = interRegular,
                fontSize = 16.sp,
                color = Color(0xFF454745),
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )


            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Your Phone Number",
                fontFamily = interMedium,
                fontSize = 14.sp,
                color = Color(0xFF454745),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    modifier = Modifier.width(80.dp),
                    textColor = Color(0xFF454745) // El gris del Figma para el texto ingresado
                )

                Spacer(modifier = Modifier.width(12.dp))

                AppTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.weight(1f),
                    textColor = Color(0xFF454745) // El gris del Figma para el texto ingresado
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            AppBottomBar(
                buttonText = "Send Code",
                onClick = { onSendCodeClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyPhoneNumberPreview() {
    VerifyPhoneNumber(
        onBackClick = { },
        onSendCodeClick = { }
    )
}