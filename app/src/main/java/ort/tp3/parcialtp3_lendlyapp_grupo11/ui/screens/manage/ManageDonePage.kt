package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun ManageDonePage(
    onExitClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val montserratBold = FontFamily(Font(R.font.montserratbold, FontWeight.Bold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close_manage),
                contentDescription = "Exit",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(48.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // saco el sombreado gris por defecto de Android
                        onClick = onExitClick
                    )
            )

            Image(
                painter = painterResource(id = R.drawable.ic_logo_minimal),
                contentDescription = "Lendly minimal logo",
                modifier = Modifier
                    .width(116.5.dp)
                    .height(40.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(id = R.drawable.ic_checkmark_artistic),
            contentDescription = "Success checkmark",
            modifier = Modifier
                .width(183.dp)
                .height(330.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "ALL DONE!",
            fontFamily = montserratBold,
            fontSize = 36.sp,
            color = Color(0xFF000000),
            lineHeight = 44.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your info was saved",
            fontFamily = interRegular,
            fontSize = 22.sp,
            color = Color(0xFF454745),
            lineHeight = 28.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            text = "Done",
            modifier = Modifier.fillMaxWidth(),
            onClick = onDoneClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManageDonePagePreview() {
    ManageDonePage(onExitClick = {}, onDoneClick = {})
}