package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenDark2

@Composable
fun HomeTopBar(
    avatarUrl: String,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(avatarUrl = avatarUrl)
            Spacer(modifier = Modifier.weight(1f))
            NotificationBellButton(onClick = onNotificationClick)
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Lendly logo",
            modifier = Modifier.size(width = 54.dp, height = 28.dp)
        )
    }
}

@Composable
private fun UserAvatar(avatarUrl: String) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(GreenDark2),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun NotificationBellButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(GreenDark2)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(17.dp)) {
            val strokeWidth = 1.6.dp.toPx()
            val bell = Path().apply {
                moveTo(size.width * 0.26f, size.height * 0.70f)
                cubicTo(
                    size.width * 0.30f, size.height * 0.58f,
                    size.width * 0.30f, size.height * 0.36f,
                    size.width * 0.50f, size.height * 0.28f
                )
                cubicTo(
                    size.width * 0.70f, size.height * 0.36f,
                    size.width * 0.70f, size.height * 0.58f,
                    size.width * 0.74f, size.height * 0.70f
                )
                lineTo(size.width * 0.82f, size.height * 0.78f)
                lineTo(size.width * 0.18f, size.height * 0.78f)
                close()
            }

            drawPath(
                path = bell,
                color = BlackFont,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawLine(
                color = BlackFont,
                start = Offset(size.width * 0.44f, size.height * 0.22f),
                end = Offset(size.width * 0.56f, size.height * 0.22f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawCircle(
                color = BlackFont,
                radius = 1.4.dp.toPx(),
                center = Offset(size.width * 0.50f, size.height * 0.86f)
            )
        }
    }
}
