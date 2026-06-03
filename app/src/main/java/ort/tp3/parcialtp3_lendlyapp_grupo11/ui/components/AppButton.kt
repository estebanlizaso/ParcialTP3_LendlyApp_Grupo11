package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts


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
    isLoading: Boolean = false,
    type: ButtonType = ButtonType.FILLED,
    backgroundColor: Color = Green,
    textColor: Color = Color.Black,
    borderColor: Color? = null,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 100.dp,
    height: Dp = 48.dp,
    horizontalPadding: Dp = 24.dp,
    fillMaxWidth: Boolean = true,
    fontSize: TextUnit = 16.sp,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    icon: (@Composable () -> Unit)? = null,
    iconSpacing: Dp = 8.dp
) {
    val shape = RoundedCornerShape(cornerRadius)

    val finalBackgroundColor = if (enabled && !isLoading) {
        if (type == ButtonType.FILLED) backgroundColor else Color.Transparent
    } else {
        if (type == ButtonType.FILLED) Color.LightGray else Color.Transparent
    }
    
    val finalTextColor = if (enabled && !isLoading) textColor else Color.Gray
    val finalBorderColor = if (enabled && !isLoading) (borderColor ?: backgroundColor) else Color.Gray

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
        .clickable(enabled = enabled && !isLoading) { onClick() }

    Box(
        modifier = finalModifier,
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = finalTextColor,
                strokeWidth = 2.dp
            )
        } else {
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
                    fontFamily = interFonts,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    letterSpacing = letterSpacing
                )
            }
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
        // Estilo exacto de la imagen (Filled, 48dp, 100px radio, 24px padding)
        AppButton(
            text = "Log In",
            type = ButtonType.FILLED
        )

        // Botón deshabilitado
        AppButton(
            text = "Disabled Button",
            enabled = false
        )

        // Botón con Icono (Show Icon = True)
        AppButton(
            text = "Send Code",
            icon = {
                // Ejemplo de icono simple
                Box(Modifier.size(18.dp).background(Color.Black, RoundedCornerShape(4.dp)))
            }
        )

        // Botón Outlined
        AppButton(
            text = "Outlined Button",
            type = ButtonType.OUTLINED,
            borderColor = Color.Black
        )
    }
}
