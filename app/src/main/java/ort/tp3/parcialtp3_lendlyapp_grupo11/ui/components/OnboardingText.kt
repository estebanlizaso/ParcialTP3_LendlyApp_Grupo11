package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenSubtitleText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenTitleText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.montserratFonts

@Composable
fun OnboardingText(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = ""
) {
    Column(
        modifier = modifier
            .width(361.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = montserratFonts,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            textAlign = TextAlign.Center,
            color = GreenTitleText,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        if (subtitle.isNotEmpty()) {
            Spacer(Modifier.height(20.dp))

            Text(
                text = subtitle,
                style = TextStyle(
                    fontFamily = interFonts,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                    lineBreak = LineBreak.Heading
                ),
                color = GreenSubtitleText,
                modifier = Modifier.padding(horizontal = 48.dp)
            )
        }
    }
}
