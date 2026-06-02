package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

enum class ButtonType {
    FILLED,
    OUTLINED,
    TEXT
}

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    type: ButtonType = ButtonType.FILLED,
    backgroundColor: Color = Green,
    textColor: Color = Color.Black,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 12.dp,
    height: Dp = 56.dp,
    horizontalPadding: Dp = 16.dp,
    fillMaxWidth: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    iconSpacing: Dp = 8.dp
) {
    val shape = RoundedCornerShape(cornerRadius)
    
    val finalBackgroundColor = if (enabled) {
        if (type == ButtonType.FILLED) backgroundColor else Color.Transparent
    } else {
        if (type == ButtonType.FILLED) Color.LightGray else Color.Transparent
    }
    
    val finalTextColor = if (enabled) textColor else Color.Gray
    val finalBorderColor = if (enabled) (borderColor ?: backgroundColor) else Color.Gray

    val finalModifier = modifier
        .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
        .height(height)
        .clip(shape)
        .then(
            if (type != ButtonType.TEXT) {
                var m = Modifier.background(color = finalBackgroundColor, shape = shape)
                if (borderColor != null || type == ButtonType.OUTLINED) {
                    m = m.border(width = borderWidth, color = finalBorderColor, shape = shape)
                }
                m
            } else Modifier
        )
        .clickable(enabled = enabled) { onClick() }

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(iconSpacing))
            }
            Text(
                text = text,
                color = finalTextColor,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interSemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppButton(text = "Standard Button")
        AppButton(
            text = "Figma Style",
            cornerRadius = 100.dp,
            height = 48.dp
        )
    }
}
