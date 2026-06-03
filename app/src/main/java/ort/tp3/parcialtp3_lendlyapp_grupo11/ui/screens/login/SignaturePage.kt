package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.icons.InfoIcon

@Composable
fun SignaturePage(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 24.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            AppTopBar(
                onBackClick = onBackClick,
                rightIcon = { InfoIcon(onClick = { /* Acción de Info */ }) }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "Let’s seal the deal!",
                fontFamily = montserratSemiBold,
                fontSize = 28.sp,
                color = Color(0xFF171D1E),
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You can use your finger or a compatible\nstylus to write your signature",
                fontFamily = interRegular,
                fontSize = 16.sp,
                color = Color(0xFF454745),
                lineHeight = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF9FAFB)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_draw_signature),
                contentDescription = "Draw signature icon",
                tint = Color(0xFF171D1E),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .size(32.dp)
            )

            Text(
                text = "Sign here\n(same signature as with the\ndocument you provided)",
                fontFamily = interMedium,
                fontSize = 14.sp,
                color = Color(0xFF6A6C6A),
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFE5E2E1) // El mismo gris exacto que sacaste del Figma antes
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "By tapping “Next”, you confirm that the\ninformation you provided is true and correct.",
                fontFamily = interRegular,
                fontSize = 16.sp,
                color = Color(0xFF454745),
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                text = "Next",
                modifier = Modifier.fillMaxWidth(),
                onClick = onNextClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignaturePagePreview() {
    SignaturePage(
        onBackClick = {},
        onNextClick = {}
    )
}