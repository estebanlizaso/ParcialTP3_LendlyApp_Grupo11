package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.AppTextField

@Composable
fun ProfileDetailPage(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: ManageViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ManageUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF5ED366))
            }
        }

        is ManageUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = (uiState as ManageUiState.Error).message, color = Color.Red)
            }
        }

        is ManageUiState.Success -> {
            val user = (uiState as ManageUiState.Success).user

            // datos de la api para rellenar

            // separar full name en nom y ape
            val nameParts = user.fullName.split(" ")
            var firstName by remember(user) { mutableStateOf(nameParts.firstOrNull() ?: "") }
            var lastName by remember(user) { mutableStateOf(nameParts.drop(1).joinToString(" ")) }

            // separar fecha
            val dateParts = user.birthDate?.split("-")
            var year by remember(user) { mutableStateOf(dateParts?.getOrNull(0) ?: "") }
            var month by remember(user) { mutableStateOf(dateParts?.getOrNull(1) ?: "") }
            var day by remember(user) { mutableStateOf(dateParts?.getOrNull(2) ?: "") }

            // separar numero en codigo de area y num
            val phoneParts = user.phone.split("-")
            var countryCode by remember(user) { mutableStateOf(phoneParts.getOrNull(0) ?: "") }
            var phoneNumber by remember(user) {
                mutableStateOf(
                    phoneParts.getOrNull(1) ?: user.phone
                )
            }

            // desarmar direccion
            val addressParts = user.address?.split(",")?.map { it.trim() } ?: emptyList()

            // lo que esta antes de la primera coma ("456 Mabini St.")
            var address by remember(user) { mutableStateOf(addressParts.getOrNull(0) ?: "") }

            // lo que está despues de la primera coma ("Quezon City")
            var city by remember(user) { mutableStateOf(addressParts.getOrNull(1) ?: "") }

            // codigo postal vacio porque la API de Postman no lo incluye
            var postalCode by remember(user) { mutableStateOf("") }


            val montserratSemiBold =
                FontFamily(Font(R.font.montserratsemibold, FontWeight.SemiBold))
            val interMedium = FontFamily(Font(R.font.intermedium, FontWeight.Medium))
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
                    AppTopBar(
                        onBackClick = onBackClick,
                        showInfoIcon = false
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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

                    AppTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        labelText = "Full legal first and middle name(s)",
                        labelColor = darkLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = darkBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        labelText = "Full legal last name",
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
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        AppTextField(
                            value = day,
                            onValueChange = { day = it },
                            modifier = Modifier.weight(1f),
                            labelText = "Day",
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                        AppTextField(
                            value = month,
                            onValueChange = { month = it },
                            modifier = Modifier.weight(1f),
                            labelText = "Month",
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                        AppTextField(
                            value = year,
                            onValueChange = { year = it },
                            modifier = Modifier.weight(1.5f),
                            labelText = "Year",
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // -- CAMPOS CON BORDE CLARITO --
                    AppTextField(
                        value = address,
                        onValueChange = { address = it },
                        labelText = "Address",
                        labelColor = lightLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = lightBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = city,
                        onValueChange = { city = it },
                        labelText = "City",
                        labelColor = lightLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = lightBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        labelText = "Postal Code",
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppTextField(
                            value = countryCode,
                            onValueChange = { countryCode = it },
                            modifier = Modifier.width(80.dp),
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        AppTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier.weight(1f),
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    AppBottomBar(
                        buttonText = "Save",
                        onClick = onSaveClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDetailPagePreview() {
    ProfileDetailPage(
        onBackClick = {},
        onSaveClick = {}
    )
}