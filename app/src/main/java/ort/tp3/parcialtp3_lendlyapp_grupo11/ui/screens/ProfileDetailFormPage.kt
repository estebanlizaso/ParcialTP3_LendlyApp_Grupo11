package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTextField
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar

@Composable
fun ProfileDetailFormPage(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // variables de estado para todos los campos
    var firstName by remember { mutableStateOf("John D.") }
    var lastName by remember { mutableStateOf("Doe") }
    var day by remember { mutableStateOf("08") }
    var month by remember { mutableStateOf("12") }
    var year by remember { mutableStateOf("1997") }
    var address by remember { mutableStateOf("Somewhere IN BLOCK 12") }
    var city by remember { mutableStateOf("Davao City") }
    var postalCode by remember { mutableStateOf("8000") }
    var countryCode by remember { mutableStateOf("+65") }
    var phone by remember { mutableStateOf("991251255") }

    val montserratSemiBold = FontFamily(Font(R.font.montserratsemibold, FontWeight.SemiBold))
    val interMedium = FontFamily(Font(R.font.intermedium, FontWeight.Medium))

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
                    value = phone,
                    onValueChange = { phone = it },
                    modifier = Modifier.weight(1f),
                    textColor = inputTextGray,
                    unfocusedBorderColor = lightBorder
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            AppBottomBar(
                buttonText = "Next",
                onClick = onNextClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDetailFormPagePreview() {
    ProfileDetailFormPage(
        onBackClick = {},
        onNextClick = {}
    )
}