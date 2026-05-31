package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.manage.ManageOptionItem

@Composable
fun CreditScorePage(
    onBackClick: () -> Unit,
    onOptionClick: (String) -> Unit
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserratsemibold, FontWeight.SemiBold))
    val montserratBold = FontFamily(Font(R.font.montserratbold, FontWeight.Bold))
    val interSemiBold = FontFamily(Font(R.font.intersemibold, FontWeight.SemiBold))
    val interMedium = FontFamily(Font(R.font.intermedium, FontWeight.Medium))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(
                onBackClick = onBackClick,
                showInfoIcon = false
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Credit Score",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFCF8F8),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_credit_score_meter),
                        contentDescription = "Credit Score Meter",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Fit
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .offset(y = (-16).dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "300",
                            fontFamily = interSemiBold,
                            fontSize = 22.sp,
                            color = Color(0xFF6A6C6A)
                        )
                        Text(
                            text = "850",
                            fontFamily = interSemiBold,
                            fontSize = 22.sp,
                            color = Color(0xFF6A6C6A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "720",
                    fontFamily = montserratBold,
                    fontSize = 48.sp,
                    color = Color(0xFF000000)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF6A6C6A))) {
                            append("Your Score is ")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF171D1E))) {
                            append("Good")
                        }
                    },
                    fontFamily = interSemiBold,
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFE5E2E1)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "What is Credit Score?",
                        fontFamily = interSemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF6A6C6A)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This is your trust score, used as a bases to determine the various activities you do on Credit Score.",
                        fontFamily = interRegular,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color(0xFF6A6C6A),
                        textAlign = TextAlign.Start
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

            // opciones
            ManageOptionItem(iconId = R.drawable.ic_account_details, title = "Account details") { onOptionClick("Account details") }
            ManageOptionItem(iconId = R.drawable.ic_email_phone, title = "Receiving by email or phone") { onOptionClick("Email/Phone") }
            ManageOptionItem(iconId = R.drawable.ic_scheduled_pay, title = "Scheduled pay") { onOptionClick("Scheduled pay") }
            ManageOptionItem(iconId = R.drawable.ic_settings, title = "Settings") { onOptionClick("Settings") }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreditScorePagePreview() {
    CreditScorePage(
        onBackClick = {},
        onOptionClick = {}
    )
}