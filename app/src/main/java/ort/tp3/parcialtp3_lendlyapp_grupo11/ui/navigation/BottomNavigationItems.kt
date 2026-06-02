package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.components.BottomNavItem

object BottomNavIndex {
    const val HOME = 0
    const val LOAN = 1
    const val SHOP = 2
    const val HISTORY = 3
    const val MANAGE = 4
}

fun lendlyBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(label = "Home", iconText = "H", route = AppRoute.HOME),
        BottomNavItem(label = "Loan", iconText = "L", route = AppRoute.LOAN),
        BottomNavItem(label = "Shop", iconText = "S", route = AppRoute.SHOP),
        BottomNavItem(label = "History", iconText = "R", route = AppRoute.HISTORY),
        BottomNavItem(label = "Manage", iconText = "M", route = AppRoute.MANAGE)
    )
}

fun selectedBottomNavIndex(route: String?): Int {
    return lendlyBottomNavItems().indexOfFirst { item -> item.route == route }
}
