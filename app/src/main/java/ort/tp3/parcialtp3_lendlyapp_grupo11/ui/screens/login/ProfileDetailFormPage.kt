package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTopBar

@Composable
fun ProfileDetailFormPage(
    viewModel: RegisterViewModel,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interMedium = FontFamily(Font(R.font.intermedium, FontWeight.Medium))
    val uiState = viewModel.uiState

    // colores
    val darkLabelColor = Color(0xFF454745)
    val lightLabelColor = Color(0xFF6A6C6A)
    val inputTextGray = Color(0xFF6A6C6A)
    val darkBorder = Color(0xFF6A6C6A)
    val lightBorder = Color(0xFFE5E2E1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(onBackClick = onBackClick)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // hacemos que se pueda scrollear
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Enter your personal\ndetails",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E),
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Global Error message display (if any, like server errors)
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            AppTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.firstName = it },
                labelText = "Full legal first and middle name(s)",
                errorMessage = viewModel.firstNameError,
                labelColor = darkLabelColor,
                textColor = inputTextGray,
                unfocusedBorderColor = darkBorder
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.lastName = it },
                labelText = "Full legal last name",
                errorMessage = viewModel.lastNameError,
                labelColor = darkLabelColor,
                textColor = inputTextGray,
                unfocusedBorderColor = darkBorder
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Date of birth",
                fontFamily = interMedium,
                fontSize = 14.sp,
                color = darkLabelColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                AppTextField(
                    value = viewModel.day,
                    onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) viewModel.day = it },
                    modifier = Modifier.weight(1f),
                    labelText = "Day",
                    errorMessage = viewModel.dayError,
                    labelColor = lightLabelColor,
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )
                AppTextField(
                    value = viewModel.month,
                    onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) viewModel.month = it },
                    modifier = Modifier.weight(1f),
                    labelText = "Month",
                    errorMessage = viewModel.monthError,
                    labelColor = lightLabelColor,
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )
                AppTextField(
                    value = viewModel.year,
                    onValueChange = { if (it.length <= 4 && it.all { char -> char.isDigit() }) viewModel.year = it },
                    modifier = Modifier.weight(1.5f),
                    labelText = "Year",
                    errorMessage = viewModel.yearError,
                    labelColor = lightLabelColor,
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                value = viewModel.address,
                onValueChange = { viewModel.address = it },
                labelText = "Address",
                errorMessage = viewModel.addressError,
                labelColor = lightLabelColor,
                textColor = inputTextGray,
                unfocusedBorderColor = lightBorder
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                value = viewModel.city,
                onValueChange = { viewModel.city = it },
                labelText = "City",
                errorMessage = viewModel.cityError,
                labelColor = lightLabelColor,
                textColor = inputTextGray,
                unfocusedBorderColor = lightBorder
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                value = viewModel.postalCode,
                onValueChange = { if (it.length <= 4 && it.all { char -> char.isDigit() }) viewModel.postalCode = it },
                labelText = "Postal Code",
                errorMessage = viewModel.postalCodeError,
                labelColor = lightLabelColor,
                textColor = inputTextGray,
                unfocusedBorderColor = lightBorder
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Phone Number",
                fontFamily = interMedium,
                fontSize = 14.sp,
                color = darkLabelColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                AppTextField(
                    value = viewModel.countryCode,
                    onValueChange = { if (it.length <= 2 && it.all { char -> char.isDigit() }) viewModel.countryCode = it },
                    modifier = Modifier.width(80.dp),
                    prefix = { Text("+") },
                    errorMessage = viewModel.countryCodeError,
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )

                Spacer(modifier = Modifier.width(12.dp))

                AppTextField(
                    value = viewModel.phone,
                    onValueChange = { if (it.length <= 8 && it.all { char -> char.isDigit() }) viewModel.phone = it },
                    modifier = Modifier.weight(1f),
                    errorMessage = viewModel.phoneError,
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            AppBottomBar(
                buttonText = "Next",
                onClick = {
                    if (viewModel.validateProfileDetails()) {
                        onNextClick()
                    }
                }
            )
        }
    }
}
