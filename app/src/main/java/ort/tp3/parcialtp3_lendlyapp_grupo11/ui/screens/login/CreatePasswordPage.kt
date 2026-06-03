package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTopBar

@Composable
fun CreatePasswordPage(
    viewModel: RegisterViewModel,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState

    // registro en API exitoso
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNextClick()
            viewModel.resetState()
        }
    }
    
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
                onBackClick = onBackClick,
                onInfoClick = { /* Info */ }
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

            // Mostrar mensaje de error global si existe
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontFamily = interRegular,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            AppTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                labelText = "Choose a password",
                errorMessage = viewModel.passwordError,
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
                text = buildAnnotatedString { 
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
                fontFamily = interRegular,
                fontSize = 14.sp,
                color = Color(0xFF454745),
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            AppBottomBar(
                buttonText = if (uiState.isLoading) "Loading..." else "Next",
                onClick = {
                    if (!uiState.isLoading) {
                        viewModel.register()
                    }
                }
            )
        }
    }
}