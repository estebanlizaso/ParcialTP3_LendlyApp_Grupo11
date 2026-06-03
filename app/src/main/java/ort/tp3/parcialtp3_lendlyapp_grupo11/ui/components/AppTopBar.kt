package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackIcon
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackTopBarFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.robotoFonts

@Composable
fun AppTopBar(
    onLeftClick: () -> Unit,
    modifier: Modifier = Modifier,
    centerText: String = "",
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null
) {
    val iconColor = BlackIcon
    val leftInteractionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = leftInteractionSource,
                        indication = null,
                        onClick = onLeftClick
                    )
            ) {
                if (leftIcon != null) {
                    leftIcon()
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }

        if (centerText.isNotEmpty()) {
            Text(
                text = centerText,
                style = TextStyle(
                    fontFamily = robotoFonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    color = BlackTopBarFont,
                    textAlign = TextAlign.Center
                )
            )
        }

        if (rightIcon != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                rightIcon()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    AppTopBar(
        onLeftClick = {}
    )
}
