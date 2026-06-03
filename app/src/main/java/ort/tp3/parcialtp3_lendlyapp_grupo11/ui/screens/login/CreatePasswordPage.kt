package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.RegisterUiState
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.RegisterViewModel

@Composable
fun CreatePasswordPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // instanciar ViewModel y SessionManager
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel: RegisterViewModel =
        remember { RegisterViewModel(sessionManager = sessionManager) }
    val uiState by viewModel.uiState.collectAsState()

    // registro en API exitoso
    LaunchedEffect(uiState) {
        if (uiState is RegisterUiState.Success) {
            onNextClick()
            viewModel.resetState()
        }
    }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interBold = FontFamily(Font(R.font.interbold, FontWeight.Bold))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(
                onLeftClick = onBackClick,
                rightIcon = { InfoIcon(onClick = { /* Info */ }) }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // Título
            Text(
                text = "Create your password",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E),
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            AppTextField(
                value = password,
                onValueChange = { password = it },
                labelText = "Choose a password",
                labelColor = Color(0xFF454745),
                unfocusedBorderColor = Color(0xFF6A6C6A),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        if (passwordVisible) {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Hide password",
                                tint = Color(0xFF454745),
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_visibility_off),
                                contentDescription = "Show password",
                                tint = Color(0xFF454745),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString { //lo que nos deja tener varios estilos en el mismo text
                    append("At least ")
                    withStyle(style = SpanStyle(fontFamily = interBold)) {
                        append("9 characters")
                    }
                    append(", containing ")
                    withStyle(style = SpanStyle(fontFamily = interBold)) {
                        append("a letter")
                    }
                    append(" and ")
                    withStyle(style = SpanStyle(fontFamily = interBold)) {
                        append("a number")
                    }
                },
                fontFamily = interRegular, //el resto de texto se ve asi
                fontSize = 14.sp,
                color = Color(0xFF454745),
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .navigationBarsPadding() //evitar superposición con barra android
                .padding(bottom = 8.dp)
        ) {
            AppBottomBar(
                buttonText = if (uiState is RegisterUiState.Loading) "Loading..." else "Next",
                onClick = {
                    if (uiState !is RegisterUiState.Loading) {
                        // API con datos hardcodeados basados en Figma + la password real
                        viewModel.register(
                            firstName = "John D.",
                            lastName = "Doe",
                            day = "08",
                            month = "12",
                            year = "1997",
                            address = "Somewhere IN BLOCK 12",
                            city = "Davao City",
                            postalCode = "8000",
                            countryCode = "+65",
                            phone = "991251255",
                            password = password
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePasswordPagePreview() {
    CreatePasswordPage(
        onBackClick = {},
        onNextClick = {}
    )
}