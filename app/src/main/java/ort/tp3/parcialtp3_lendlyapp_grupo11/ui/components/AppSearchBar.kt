package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayColor
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun AppSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search..."
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = BlackFont,
            fontSize = 14.sp,
            fontFamily = interSemiBold
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(1.dp, Color(0xFFC7C7C7), RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q",
                    color = GrayColor,
                    fontSize = 16.sp,
                    fontFamily = interSemiBold
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = GrayColor,
                            fontSize = 14.sp,
                            fontFamily = interSemiBold
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}
