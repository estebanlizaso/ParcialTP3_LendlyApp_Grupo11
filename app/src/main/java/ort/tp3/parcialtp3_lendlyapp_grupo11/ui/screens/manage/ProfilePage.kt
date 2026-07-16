package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.HomeTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.ManageOptionItem

@Composable
fun ProfilePage(
    onOptionClick: (String) -> Unit,
    onEditClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onNotificationClick: () -> Unit = {},
    viewModel: ManageViewModel = hiltViewModel()
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val uiState = viewModel.uiState //estado API

    var showLogoutDialog by remember { mutableStateOf(false) }

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
    } else if (uiState.isSuccess && uiState.user != null) {
        val user = uiState.user

        // nombre a iniciales
        val initials = user.fullName.split(" ")
            .mapNotNull { it.firstOrNull()?.toString() }
            .joinToString("")
            .take(2)
            .uppercase()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            HomeTopBar(
                avatarUrl = user.avatar,
                modifier = Modifier.padding(horizontal = 24.dp),
                onNotificationClick = onNotificationClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Manage",
                    fontFamily = montserratSemiBold,
                    fontSize = 28.sp,
                    color = Color(0xFF171D1E)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Currently using as",
                    fontFamily = interMedium,
                    fontSize = 14.sp,
                    color = Color(0xFF6A6C6A)
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFE5E2E1)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(56.dp)) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF3F4F6))
                                .align(Alignment.TopStart),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initials,
                                fontFamily = montserratSemiBold,
                                fontSize = 18.sp,
                                color = Color(0xFF171D1E)
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.ic_camera_small),
                            contentDescription = "Edit photo",
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd)
                                .background(Color.White, CircleShape)
                                .padding(2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Account details",
                            fontFamily = interRegular,
                            fontSize = 16.sp,
                            color = Color(0xFF000000)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Your personal Account",
                            fontFamily = interRegular,
                            fontSize = 14.sp,
                            color = Color(0xFF6A6C6A)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(Color(0xFF4ADE80), RoundedCornerShape(8.dp))
                            .clickable { onEditClick() }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Edit",
                            fontFamily = interMedium,
                            fontSize = 14.sp,
                            color = Color(0xFF171D1E)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "General",
                    fontFamily = interMedium,
                    fontSize = 14.sp,
                    color = Color(0xFF6A6C6A),
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFE5E2E1)
                )

                Spacer(modifier = Modifier.height(8.dp))

                ManageOptionItem(
                    iconId = R.drawable.ic_account_details,
                    title = "Account details"
                ) { onOptionClick("Account details") }
                ManageOptionItem(
                    iconId = R.drawable.ic_email_phone,
                    title = "Receiving by email or phone"
                ) { onOptionClick("Email/Phone") }
                ManageOptionItem(
                    iconId = R.drawable.ic_scheduled_pay,
                    title = "Scheduled pay"
                ) { onOptionClick("Scheduled pay") }
                ManageOptionItem(
                    iconId = R.drawable.ic_credit_score,
                    title = "Credit score"
                ) { onOptionClick("Credit score") }
                ManageOptionItem(
                    iconId = R.drawable.ic_settings,
                    title = "Settings"
                ) { onOptionClick("Settings") }
                ManageOptionItem(
                    iconId = R.drawable.ic_terms,
                    title = "Terms and Conditions"
                ) { onOptionClick("Terms") }
                ManageOptionItem(
                    iconId = R.drawable.ic_help,
                    title = "Help"
                ) { onOptionClick("Help") }

                Spacer(modifier = Modifier.height(16.dp))
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color(0xFFE5E2E1)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(18.dp))

                ManageOptionItem(
                    iconId = R.drawable.ic_logout,
                    title = "Log Out"
                ) { showLogoutDialog = true }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Cerrar sesión") },
            text = { Text(text = "¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogOutClick()
                }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage(
        onOptionClick = {},
        onEditClick = {},
        onLogOutClick = {}
    )
}
