package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = null,
    errorMessage: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textColor: Color = Color.Black,
    labelColor: Color = Color(0xFF454745),
    unfocusedBorderColor: Color = Color(0xFF6A6C6A),
    enabled: Boolean = true
) {
    val interMedium = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))
    val interRegular = FontFamily(Font(R.font.interregular, FontWeight.Normal))

    Column(modifier = modifier) {
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        
        if (labelText != null) {
            val labelAlpha = if (enabled) 1.0f else 0.6f
            Text(
                text = labelText,
                fontFamily = interMedium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = labelColor.copy(alpha = labelAlpha),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            isError = errorMessage != null,
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                fontFamily = interRegular,
                fontSize = 16.sp,
                color = if (enabled) textColor else textColor.copy(alpha = 0.6f)
            ),
            visualTransformation = visualTransformation,
            prefix = prefix,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4ADE80),
                unfocusedBorderColor = unfocusedBorderColor,
                errorBorderColor = Color.Red,
                disabledBorderColor = unfocusedBorderColor.copy(alpha = 0.5f),
                disabledTextColor = textColor.copy(alpha = 0.6f),
                disabledContainerColor = Color(0xFFF9F9F9)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTextFieldPreview() {
    AppTextField(
        value = "Texto de ejemplo",
        onValueChange = {},
        labelText = "Etiqueta",
        errorMessage = "Error message"
    )
}
