package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*

@Composable
fun StepBox(
    stepTag: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Step Tag
        Surface(
            color = SplashScreenGreen,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.offset(x = (-8).dp)
        ) {
            Text(
                text = stepTag,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    color = GrayText
                )
            )
        }
        
        Spacer(Modifier.height(10.dp))
        
        // Description
        Text(
            text = description,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = Color.Black
            )
        )
        
        Spacer(Modifier.height(8.dp))
        
        // Placeholder for form component
        content()
    }
}
