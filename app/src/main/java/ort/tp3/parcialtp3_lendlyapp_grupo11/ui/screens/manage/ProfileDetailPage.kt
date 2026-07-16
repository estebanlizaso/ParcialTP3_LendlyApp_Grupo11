package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.viewmodels.LoanApplyUiState

@Composable
fun ProfileDetailPage(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val email by viewModel.email.collectAsState()
    val day by viewModel.day.collectAsState()
    val month by viewModel.month.collectAsState()
    val year by viewModel.year.collectAsState()
    val address by viewModel.address.collectAsState()
    val city by viewModel.city.collectAsState()
    val postalCode by viewModel.postalCode.collectAsState()
    val countryCode by viewModel.countryCode.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val saveState by viewModel.saveState.collectAsState()

    // estados de error individuales
    val firstNameError by viewModel.firstNameError.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val dayError by viewModel.dayError.collectAsState()
    val monthError by viewModel.monthError.collectAsState()
    val yearError by viewModel.yearError.collectAsState()
    val addressError by viewModel.addressError.collectAsState()
    val cityError by viewModel.cityError.collectAsState()
    val postalCodeError by viewModel.postalCodeError.collectAsState()
    val countryCodeError by viewModel.countryCodeError.collectAsState()
    val phoneError by viewModel.phoneError.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(saveState) {
        when (saveState) {
            is LoanApplyUiState.Success -> {
                onSaveClick()
                viewModel.resetSaveState()
            }
            is LoanApplyUiState.Error -> {
                snackbarHostState.showSnackbar((saveState as LoanApplyUiState.Error).message)
                viewModel.resetSaveState()
            }
            else -> {}
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF5ED366))
        }
    } else if (uiState.errorMessage != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = uiState.errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            AppButton(
                text = "Try again",
                onClick = { viewModel.loadUserProfile() },
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    } else {
        // usamos los flows del ViewModel que ya vienen de Firestore.

        val montserratSemiBold =
            FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
        val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))
        val darkLabelColor = Color(0xFF454745)
        val lightLabelColor = Color(0xFF6A6C6A)
        val inputTextGray = Color(0xFF6A6C6A)
        val darkBorder = Color(0xFF6A6C6A)
        val lightBorder = Color(0xFFE5E2E1)

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.White
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(top = 8.dp, bottom = 24.dp)
            ) {
                Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                    AppTopBar(
                        onLeftClick = onBackClick
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
                        onValueChange = { viewModel.onFirstNameChange(it) },
                        labelText = "Full legal first and middle name(s)",
                        errorMessage = firstNameError,
                        labelColor = darkLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = darkBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = lastName,
                        onValueChange = { viewModel.onLastNameChange(it) },
                        labelText = "Full legal last name",
                        errorMessage = lastNameError,
                        labelColor = darkLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = darkBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        labelText = "Email Address",
                        errorMessage = emailError,
                        labelColor = darkLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = darkBorder,
                        enabled = false
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
                            onValueChange = { viewModel.onDayChange(it) },
                            modifier = Modifier.weight(1f),
                            labelText = "Day",
                            errorMessage = dayError,
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                        AppTextField(
                            value = month,
                            onValueChange = { viewModel.onMonthChange(it) },
                            modifier = Modifier.weight(1f),
                            labelText = "Month",
                            errorMessage = monthError,
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                        AppTextField(
                            value = year,
                            onValueChange = { viewModel.onYearChange(it) },
                            modifier = Modifier.weight(1.5f),
                            labelText = "Year",
                            errorMessage = yearError,
                            labelColor = lightLabelColor,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // -- CAMPOS CON BORDE CLARITO --
                    AppTextField(
                        value = address,
                        onValueChange = { viewModel.onAddressChange(it) },
                        labelText = "Address",
                        errorMessage = addressError,
                        labelColor = lightLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = lightBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = city,
                        onValueChange = { viewModel.onCityChange(it) },
                        labelText = "City",
                        errorMessage = cityError,
                        labelColor = lightLabelColor,
                        textColor = inputTextGray,
                        unfocusedBorderColor = lightBorder
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    AppTextField(
                        value = postalCode,
                        onValueChange = { viewModel.onPostalCodeChange(it) },
                        labelText = "Postal Code",
                        errorMessage = postalCodeError,
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
                            onValueChange = { viewModel.onCountryCodeChange(it) },
                            modifier = Modifier.width(80.dp),
                            errorMessage = countryCodeError,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        AppTextField(
                            value = phoneNumber,
                            onValueChange = { viewModel.onPhoneNumberChange(it) },
                            modifier = Modifier.weight(1f),
                            errorMessage = phoneError,
                            textColor = inputTextGray,
                            unfocusedBorderColor = lightBorder
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    AppBottomBar(
                        buttonText = "Save",
                        onClick = { viewModel.saveProfile() },
                        isLoading = saveState is LoanApplyUiState.Loading
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
