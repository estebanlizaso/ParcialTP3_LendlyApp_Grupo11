package ort.tp3.parcialtp3_lendlyapp_grupo11.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ShopSearchScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.FilterScreen
import ort.tp3.parcialtp3_lendlyapp_grupo11.ui.screens.shop.ProductDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Shop.route) {
        composable(Screen.Shop.route) {
            ShopScreen(
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onFilterClick = { navController.navigate(Screen.Filter.route) },
                onProductClick = { productId -> 
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }
        composable(Screen.Search.route) {
            ShopSearchScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Filter.route) {
            FilterScreen(
                onBackClick = { navController.popBackStack() },
                onApplyClick = { 
                    navController.popBackStack(Screen.Shop.route, inclusive = false)
                }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onContinueClick = {
                    navController.popBackStack(Screen.Shop.route, inclusive = false)
                }
            )
        }
    }
}
