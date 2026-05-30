package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val interBold = FontFamily(Font(R.font.interbold, FontWeight.Bold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interSemiBold = FontFamily(Font(R.font.intersemibold, FontWeight.SemiBold))

    val linkColor = Color(0xFF4C662B)

    // sacar el fondo gris del click
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.8f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Lendly Logo",
            modifier = Modifier.width(180.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF3F4F6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JD",
                    fontFamily = interBold,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "John Doe", fontFamily = interRegular, fontSize = 16.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "+63-923456790", fontFamily = interRegular, color = Color.Gray, fontSize = 14.sp)
            }

            Text(
                text = "Change",
                color = linkColor,
                fontFamily = interSemiBold,
                textDecoration = TextDecoration.Underline,
                fontSize = 14.sp,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource, //sacar fondo gris
                    indication = null
                ) { /* ir a pantalla cambiar cuenta */ }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        AppTextField(
            value = password,
            onValueChange = { password = it },
            labelText = "Password",
            modifier = Modifier.padding(horizontal = 24.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (passwordVisible) {
                        // ON: icono de Material
                        Icon(
                            imageVector = Icons.Outlined.Visibility,
                            contentDescription = "Hide password",
                            tint = Color(0xFF454745),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        // OFF: icono SVG de Figma
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

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Forgot your password?",
            color = linkColor,
            fontFamily = interSemiBold,
            textDecoration = TextDecoration.Underline,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { /* accion de recuperar contraseña */ }
        )

        Spacer(modifier = Modifier.weight(1.2f))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFE5E7EB)
        )

        // distancia linea divisoria del boton
        Spacer(modifier = Modifier.height(12.dp))

        AppButton(
            text = "Log In",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            onClick = {
                onLoginSuccess()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = { }
    )
}