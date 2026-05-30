package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.pages.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.component.Logo
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.GreenText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.LightGreenText
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.montserratFonts

@Composable
fun Onboarding1() {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        )  {
            Logo()
        }



        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.onboarding_quick_loans),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.height(32.dp))

        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.onboarding1_title),
                fontFamily = montserratFonts,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
                color = GreenText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))
/*
TODO: Revisar alineación del texto
TODO: Revisar creación del botón para usarlo en esta página
*/
            Text(
                text = stringResource(R.string.onboarding1_subtitle),
                fontFamily = interFonts,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
                color = LightGreenText,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = false )
@Composable
fun Onboarding1Preview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        Onboarding1()
    }
}