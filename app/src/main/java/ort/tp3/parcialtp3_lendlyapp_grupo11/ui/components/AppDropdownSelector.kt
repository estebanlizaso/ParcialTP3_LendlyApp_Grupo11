package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreyDivider
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun AppDropdownSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowIcon = if (expanded) R.drawable.ic_arrow_up_desplegable else R.drawable.ic_arrow_down_desplegable

    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, GreyDivider), RoundedCornerShape(12.dp))
                .clickable { expanded = !expanded }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.15.sp,
                    color = BlackFont
                )
            )
            Icon(
                painter = painterResource(id = arrowIcon),
                contentDescription = null,
                tint = BlackFont,
                modifier = Modifier.size(24.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f) // Slight margin
                .background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = TextStyle(
                                fontFamily = interFonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = BlackFont
                            )
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
