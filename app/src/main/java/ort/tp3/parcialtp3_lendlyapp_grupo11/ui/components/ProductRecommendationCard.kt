package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GrayColor
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.DarkGreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenLight3
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun ProductRecommendationCard(
    title: String,
    imageUrl: String,
    price: String,
    term: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(GreenLight3)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkGreen),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = BlackFont,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$price x $term",
            color = GrayColor,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )
    }
}
