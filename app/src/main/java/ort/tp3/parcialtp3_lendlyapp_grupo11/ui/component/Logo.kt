package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = modifier.aspectRatio(117f / 40f),
        contentScale = ContentScale.Fit
    )
}