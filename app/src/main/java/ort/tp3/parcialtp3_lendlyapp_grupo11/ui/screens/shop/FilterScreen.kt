package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppButton
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ButtonType
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.Green
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun FilterScreen(
    onBackClick: () -> Unit = {},
    onApplyClick: () -> Unit = {}
) {
    var selectedBrand by remember { mutableStateOf("All") }
    var selectedGender by remember { mutableStateOf("All") }
    var selectedSort by remember { mutableStateOf("Most Recent") }
    var selectedPriceRange by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Filter",
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFonts
                )
                Box(modifier = Modifier.size(48.dp))
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppButton(
                    text = "Reset Filter",
                    type = ButtonType.OUTLINED,
                    borderColor = Color.Gray,
                    backgroundColor = Color.Transparent,
                    modifier = Modifier.weight(1f),
                    cornerRadius = 100.dp,
                    height = 48.dp,
                    onClick = {
                        selectedBrand = "All"
                        selectedGender = "All"
                        selectedSort = "Most Recent"
                        selectedPriceRange = "All"
                    }
                )
                AppButton(
                    text = "Apply",
                    type = ButtonType.FILLED,
                    backgroundColor = Green,
                    modifier = Modifier.weight(1f),
                    cornerRadius = 100.dp,
                    height = 48.dp,
                    onClick = onApplyClick
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            FilterSection(
                title = "Brands",
                options = listOf("All", "Nike", "Adidas", "Puma", "Jordan"),
                selectedOption = selectedBrand,
                onOptionSelected = { selectedBrand = it }
            )

            FilterSection(
                title = "Gender",
                options = listOf("All", "Men", "Women"),
                selectedOption = selectedGender,
                onOptionSelected = { selectedGender = it }
            )

            FilterSection(
                title = "Sort by",
                options = listOf("Most Recent", "Popular", "Low Interest"),
                selectedOption = selectedSort,
                onOptionSelected = { selectedSort = it }
            )

            FilterSection(
                title = "Price Range",
                options = listOf("All", "$500 - $1000", "$1000 - $5000"),
                selectedOption = selectedPriceRange,
                onOptionSelected = { selectedPriceRange = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FilterSection(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = interFonts
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                AppButton(
                    text = option,
                    type = if (isSelected) ButtonType.FILLED else ButtonType.OUTLINED,
                    backgroundColor = if (isSelected) Green else Color.Transparent,
                    borderColor = if (isSelected) Green else Color.Gray,
                    cornerRadius = 8.dp,
                    height = 36.dp,
                    horizontalPadding = 16.dp,
                    fillMaxWidth = false,
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen()
}
