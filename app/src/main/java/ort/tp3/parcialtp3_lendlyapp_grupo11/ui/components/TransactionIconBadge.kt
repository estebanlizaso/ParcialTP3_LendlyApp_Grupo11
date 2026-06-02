package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenDark2

enum class TransactionIconType {
    PAYMENT,
    ADD,
    CHECK
}

@Composable
fun TransactionIconBadge(
    type: TransactionIconType,
    modifier: Modifier = Modifier,
    badgeSize: Dp = 44.dp,
    iconSize: Dp = 20.dp,
    highlighted: Boolean = false
) {
    Box(
        modifier = modifier
            .size(badgeSize)
            .clip(CircleShape)
            .background(if (highlighted) Green else GreenDark2),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(iconSize)) {
            val stroke = 1.8.dp.toPx()
            when (type) {
                TransactionIconType.CHECK -> {
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.18f, size.height * 0.52f),
                        end = Offset(size.width * 0.42f, size.height * 0.74f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.42f, size.height * 0.74f),
                        end = Offset(size.width * 0.84f, size.height * 0.28f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                }

                TransactionIconType.ADD -> {
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.50f, size.height * 0.18f),
                        end = Offset(size.width * 0.50f, size.height * 0.82f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.18f, size.height * 0.50f),
                        end = Offset(size.width * 0.82f, size.height * 0.50f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                }

                TransactionIconType.PAYMENT -> {
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.50f, size.height * 0.82f),
                        end = Offset(size.width * 0.50f, size.height * 0.20f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.50f, size.height * 0.20f),
                        end = Offset(size.width * 0.22f, size.height * 0.48f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = BlackFont,
                        start = Offset(size.width * 0.50f, size.height * 0.20f),
                        end = Offset(size.width * 0.78f, size.height * 0.48f),
                        strokeWidth = stroke,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}
