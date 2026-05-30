package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AccountBalanceCard
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.AppBottomNavigationBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.BottomNavItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.HomeTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.LoanListItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ProductRecommendationCard
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.SectionHeader
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interSemiBold

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCashInClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        HomeTopBar()

        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Account",
            color = BlackFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interSemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))
        AccountBalanceCard(
            balance = "P 2,500.00",
            onCashInClick = onCashInClick
        )

        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader(title = "Unpaid Loans")

        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            LoanListItem(
                brandName = "Nike Inc.",
                amount = "P400.00",
                feeLabel = "Fees of February",
                brandInitial = "N"
            )
            LoanListItem(
                brandName = "Apple Inc.",
                amount = "P1500.00",
                feeLabel = "Fees of March",
                brandInitial = "A"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader(title = "Recommended For You")

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductRecommendationCard(
                title = "iPhone 12 Pro",
                price = "P1,200",
                term = "24 mo",
                productInitial = "P"
            )
            ProductRecommendationCard(
                title = "iPhone 12 Pro Max",
                price = "P1,200",
                term = "24 mo",
                productInitial = "H"
            )
            ProductRecommendationCard(
                title = "Running Shoes",
                price = "P1,200",
                term = "24 mo",
                productInitial = "S"
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        AppBottomNavigationBar(
            selectedIndex = 0,
            items = listOf(
                BottomNavItem(label = "Home", iconText = "H"),
                BottomNavItem(label = "Loan", iconText = "L"),
                BottomNavItem(label = "Shop", iconText = "S"),
                BottomNavItem(label = "History", iconText = "R"),
                BottomNavItem(label = "Manage", iconText = "M")
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        HomeScreen()
    }
}
