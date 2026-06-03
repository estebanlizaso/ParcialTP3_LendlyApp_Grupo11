package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.*

@Composable
fun InfoContainer(
    modifier: Modifier = Modifier,
    isProminent: Boolean = false,
    // Prominent specific
    tagText: String = "",
    tagIcon: Painter? = null,
    titleText: String = "",
    subtitleText: String = "",
    imagePainter: Painter? = null,
    // Standard specific
    borrowAmount: String = "",
    onWhatIsThisClick: () -> Unit = {},
    details: List<LoanDetailData> = emptyList()
) {
    val backgroundColor = if (isProminent) Green else Neutral98
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        if (isProminent) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp, bottom = 24.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (tagText.isNotEmpty()) {
                        TagBox(text = tagText, icon = tagIcon)
                        Spacer(Modifier.height(16.dp))
                    }

                    Text(
                        text = titleText,
                        style = TextStyle(
                            fontFamily = montserratFonts,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 28.sp,
                            lineHeight = 36.sp,
                            color = BlackFont
                        )
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = subtitleText,
                        style = TextStyle(
                            fontFamily = interFonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = BlackFont
                        )
                    )
                }

                if (imagePainter != null) {
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.BottomEnd),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "You can borrow up to",
                    style = TextStyle(
                        fontFamily = interFonts,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = borrowAmount,
                    style = TextStyle(
                        fontFamily = montserratFonts,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp,
                        lineHeight = 40.sp,
                        color = DarkGreenText
                    )
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "*Subject to evaluation",
                    style = TextStyle(
                        fontFamily = interFonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = GrayText
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = GreyDivider)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Loan Details",
                        style = TextStyle(
                            fontFamily = interFonts,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = DarkGreenText
                        )
                    )
                    
                    Text(
                        text = "What is this?",
                        modifier = Modifier.clickable { onWhatIsThisClick() },
                        style = TextStyle(
                            fontFamily = interFonts,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = MediumDarkGreenText,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = GreyDivider)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    details.forEachIndexed { index, detail ->
                        LoanDetailItem(
                            title = detail.title,
                            value = detail.value,
                            label = detail.label,
                            modifier = Modifier.weight(1f)
                        )
                        if (index < details.size - 1) {
                            VerticalDivider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = 8.dp),
                                color = GreyDivider
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagBox(text: String, icon: Painter?) {
    Surface(
        color = SplashScreenGreen,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.size(width = 166.dp, height = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = BlackFont
                )
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = BlackFont,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
private fun LoanDetailItem(
    title: String,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            style = TextStyle(
                fontFamily = montserratFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                color = DarkGreenText
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = TextStyle(
                fontFamily = interFonts,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = GrayText
            )
        )
    }
}

data class LoanDetailData(
    val title: String,
    val value: String,
    val label: String
)
