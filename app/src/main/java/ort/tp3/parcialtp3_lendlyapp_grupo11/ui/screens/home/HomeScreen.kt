package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.home

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
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.HomeTopBar
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.LoanListItem
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.ProductRecommendationCard
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.SectionHeader
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.BlackFont
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.ParcialTP3_LendlyApp_Grupo11Theme
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.theme.interFonts

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    onCashInClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
        HomeTopBar(
            avatarUrl = uiState.avatarUrl,
            onNotificationClick = onNotificationClick
        )

        Spacer(modifier = Modifier.height(34.dp))
        Text(
            text = "Account",
            color = BlackFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = interFonts
        )

        Spacer(modifier = Modifier.height(16.dp))
        AccountBalanceCard(
            balance = uiState.balance,
            onCashInClick = onCashInClick
        )

        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Loading home data...",
                color = BlackFont,
                fontSize = 13.sp,
                fontFamily = interFonts
            )
        }

        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.error,
                color = Color.Red,
                fontSize = 13.sp,
                fontFamily = interFonts
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader(title = "Unpaid Loans")

        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            uiState.loans.forEach { loan ->
                LoanListItem(
                    brandName = loan.lender,
                    logoUrl = loan.logoUrl,
                    amount = loan.amountDue,
                    feeLabel = loan.feeLabel
                )
            }
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
            uiState.products.forEach { product ->
                ProductRecommendationCard(
                    title = product.name,
                    imageUrl = product.imageUrl,
                    price = product.monthlyInstallment,
                    term = product.months
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ParcialTP3_LendlyApp_Grupo11Theme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                avatarUrl = "https://i.pravatar.cc/150?img=3",
                balance = "₱2,500.00",
                loans = listOf(
                    HomeLoanUi("Nike Inc.", "https://logo.clearbit.com/nike.com", "₱400.00", "Fees of February"),
                    HomeLoanUi("Apple Inc.", "https://logo.clearbit.com/apple.com", "₱1,500.00", "Fees of March")
                ),
                products = listOf(
                    HomeProductUi("iPhone 12 Pro", "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-12-pro-family-hero", "₱1,200.00", "24 mo"),
                    HomeProductUi("AirPods Pro", "https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/MME73", "₱600.00", "24 mo")
                )
            )
        )
    }
}
