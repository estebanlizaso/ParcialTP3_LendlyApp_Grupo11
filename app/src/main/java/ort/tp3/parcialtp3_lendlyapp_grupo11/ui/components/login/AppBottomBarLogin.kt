package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton

@Composable
fun AppBottomBar(
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color(0xFFE5E7EB)
        )

        Spacer(modifier = Modifier.height(12.dp))

        AppButton(
            text = buttonText,
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    AppBottomBar(
        buttonText = "Continuar",
        onClick = {}
    )
}
