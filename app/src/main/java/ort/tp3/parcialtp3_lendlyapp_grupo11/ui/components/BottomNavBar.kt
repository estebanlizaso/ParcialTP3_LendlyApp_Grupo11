package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ort.tp3.parcialtp3_lendlyapp_grupo11.R

@Composable
fun BottomNavBar(
    selectedRoute: String = "Manage",
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem("Home", R.drawable.ic_nav_home, selectedRoute == "Home") { onNavigate("Home") }
        NavItem("Loan", R.drawable.ic_nav_loan, selectedRoute == "Loan") { onNavigate("Loan") }
        NavItem("Shop", R.drawable.ic_nav_shop, selectedRoute == "Shop") { onNavigate("Shop") }
        NavItem("History", R.drawable.ic_nav_history, selectedRoute == "History") { onNavigate("History") }
        NavItem("Manage", R.drawable.ic_nav_manage, selectedRoute == "Manage") { onNavigate("Manage") }
    }
}

@Composable
private fun NavItem(
    title: String,
    iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interMedium = FontFamily(Font(R.font.intermedium, FontWeight.Medium))
    val interSemiBold = FontFamily(Font(R.font.intersemibold, FontWeight.SemiBold))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(32.dp)
                .background(
                    color = if (isSelected) Color(0xFFE5F5EA) else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontFamily = if (isSelected) interSemiBold else interMedium,
            fontSize = 12.sp,
            color = if (isSelected) Color(0xFF171D1E) else Color(0xFF6A6C6A)
        )
    }
}