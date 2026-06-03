package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon

@Composable
fun VerifiedPage(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interSemiBold = FontFamily(Font(R.font.intersemibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            AppTopBar(
                onLeftClick = onBackClick,
                rightIcon = { InfoIcon(onClick = { /* Acción de Info */ }) }
            )
        }

        Spacer(modifier = Modifier.height(52.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_shield_check),
            contentDescription = "Verification success shield",
            modifier = Modifier.size(100.dp) // Tamaño estimado según el Figma
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Woah, Your face and ID\nare the same!",
            fontFamily = montserratSemiBold,
            fontSize = 28.sp,
            color = Color(0xFF171D1E),
            lineHeight = 36.sp,
            textAlign = TextAlign.Center, // Centramos el texto
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "We are just a few questions away from\nopening your own lendly loan account. Tap\nthe button to continue.",
            fontFamily = interRegular,
            fontSize = 14.sp,
            color = Color(0xFF454745),
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color(0xFFF9FAFB), RoundedCornerShape(16.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Security Guard",
                    fontFamily = interSemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF171D1E),
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Our online security feature world-class\nprotection against hackers. It makes\nthem cry and rethink their purpose\nin life.",
                    fontFamily = interRegular,
                    fontSize = 14.sp,
                    color = Color(0xFF454745),
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            AppBottomBar(
                buttonText = "Next",
                onClick = onNextClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifiedPagePreview() {
    VerifiedPage(
        onBackClick = {},
        onNextClick = {}
    )
}