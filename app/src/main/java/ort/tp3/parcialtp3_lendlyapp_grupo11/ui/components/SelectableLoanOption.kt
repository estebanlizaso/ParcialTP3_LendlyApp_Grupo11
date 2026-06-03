package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*

data class LoanOptionData(
    val title: String,
    val subtitle: String,
    val rightValue: String
)

@Composable
fun SelectableLoanOption(
    data: LoanOptionData,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) Green else GreyDivider
    
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.title,
                    style = TextStyle(
                        fontFamily = interFonts,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        color = BlackFont
                    )
                )
                Text(
                    text = data.subtitle,
                    style = TextStyle(
                        fontFamily = interFonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.4.sp,
                        color = BlackFont
                    )
                )
            }
            
            Text(
                text = data.rightValue,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    color = BlackFont
                )
            )
        }
    }
}
