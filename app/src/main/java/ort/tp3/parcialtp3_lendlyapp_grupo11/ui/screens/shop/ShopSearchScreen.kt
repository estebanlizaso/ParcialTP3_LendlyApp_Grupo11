package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppLabel
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun ShopSearchScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var recentSearches by remember {
        mutableStateOf(
            listOf(
                "Blue shirt", "Red shirt", "Yellow shirt",
                "Blue Shoes", "Yellow Shoes", "Red Shoes",
                "Yellow Shoes", "Red Shoes", "Blue Shoes",
                "Yellow shirt"
            )
        )
    }

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
                    text = "Search",
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interSemiBold
                )
                // Spacer to balance the back button
                Box(modifier = Modifier.size(48.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar
            AppLabel(
                label = "",
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Search for product",
                height = 56.dp,
                cornerRadius = 12.dp,
                leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interSemiBold
                )
                Text(
                    text = "Clear All",
                    color = Color(0xFF4F772D), // Color verde oscuro de la imagen
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interSemiBold,
                    modifier = Modifier.clickable { recentSearches = emptyList() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recent Searches List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(recentSearches) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSearchClick(item) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item,
                            color = Color.Gray,
                            fontSize = 15.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    recentSearches = recentSearches.filter { it != item }
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopSearchScreenPreview() {
    ShopSearchScreen()
}
