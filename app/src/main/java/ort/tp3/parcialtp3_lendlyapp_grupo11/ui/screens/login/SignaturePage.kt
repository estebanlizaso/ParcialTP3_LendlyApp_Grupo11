package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login.AppBottomBar

@Composable
fun SignaturePage(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val montserratSemiBold = FontFamily(Font(R.font.montserrat_semibold, FontWeight.SemiBold))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    val path = remember { Path() }
    var hasDrawn by remember { mutableStateOf(false) }
    var drawTrigger by remember { mutableStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
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
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                path.moveTo(offset.x, offset.y)
                                errorMessage = null // Limpiar error al empezar a dibujar
                                drawTrigger++
                            },
                            onDrag = { change, _ ->
                                path.lineTo(change.position.x, change.position.y)
                                hasDrawn = true
                                drawTrigger++
                            }
                        )
                    }
            ) {
                drawTrigger.let { 
                    drawPath(
                        path = path,
                        color = Color.Black,
                        style = Stroke(width = 8f)
                    )
                }
            }

            if (!hasDrawn) {
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si no se ha firmado
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                fontSize = 14.sp,
                fontFamily = interRegular,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "By tapping “Next”, you confirm that the\ninformation you provided is true and correct.",
            fontFamily = interRegular,
            fontSize = 16.sp,
            color = Color(0xFF454745),
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            AppBottomBar(
                buttonText = "Next",
                onClick = {
                    if (hasDrawn) {
                        onNextClick()
                    } else {
                        errorMessage = "Please provide your signature before proceeding"
                    }
                }
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
