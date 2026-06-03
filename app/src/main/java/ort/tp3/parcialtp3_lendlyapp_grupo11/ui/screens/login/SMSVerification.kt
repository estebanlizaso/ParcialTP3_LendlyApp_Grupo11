package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.BaselineShift

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SMSVerification(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // 6 estados para cada numero del codigo
    val codeValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))
    val interSemiBold = FontFamily(Font(R.font.intersemibold, FontWeight.SemiBold))

    val linkColor = Color(0xFF005046)
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(onLeftClick = onBackClick)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Enter the code",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    append("Enter the security code we sent to\n")
                    withStyle(style = SpanStyle(baselineShift = BaselineShift(-0.25f))) {
                        append("******")
                    }
                    append("731")
                },
                fontFamily = interRegular,
                fontSize = 16.sp,
                color = Color(0xFF454745),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Mensaje de error si el código está incompleto
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = interRegular,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Text(
                text = "Code",
                fontFamily = interMedium,
                fontSize = 14.sp,
                color = Color(0xFF454745),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // fila de 6 cajitas otp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                codeValues.forEachIndexed { index, value ->
                    OtpBox(
                        value = value,
                        onValueChange = { newValue ->
                            errorMessage = null // Limpiar error al escribir
                            if (newValue.isEmpty()) {
                                codeValues[index] = ""
                                if (index > 0) focusRequesters[index - 1].requestFocus()
                            } else {
                                val digit = newValue.lastOrNull()
                                if (digit != null && digit.isDigit()) {
                                    codeValues[index] = digit.toString()
                                    if (index < 5) focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        focusRequester = focusRequesters[index],
                        interRegular = interRegular
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Didn't received a code?",
                    color = linkColor,
                    fontFamily = interSemiBold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { /* reenviar SMS */ }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            AppBottomBar(
                buttonText = "Next",
                onClick = {
                    if (codeValues.all { it.isNotEmpty() }) {
                        onNextClick()
                    } else {
                        errorMessage = "Please enter the complete 6-digit code"
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    interRegular: FontFamily
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .size(width = 52.5.dp, height = 56.dp)
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontFamily = interRegular,
            fontSize = 18.sp,
            color = Color(0xFF454745),
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF4ADE80),
            unfocusedBorderColor = Color(0xFF6A6C6A)
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun SMSVerificationPreview() {
    SMSVerification(onBackClick = {}, onNextClick = {})
}