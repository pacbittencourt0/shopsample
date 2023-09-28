package com.pacbittencourt.shop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pacbittencourt.shop.ui.cart.CartScreen
import com.pacbittencourt.shop.ui.orderdetails.OrderDetailsScreen
import com.pacbittencourt.shop.ui.orders.OrdersScreen
import com.pacbittencourt.shop.ui.products.ProductsScreen

@Composable
fun ShopNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Products.route) {
        composable(route = Products.route) {
            ProductsScreen(modifier = modifier)
        }
        composable(route = Orders.route) {
            OrdersScreen(modifier = modifier) { orderId: String ->
                navController.navigate("${OrderDetails.simpleRoute}/$orderId")
            }
        }
        composable(route = Cart.route) {
            CartScreen(modifier = modifier)
        }
        composable(
            route = OrderDetails.route,
            arguments = OrderDetails.arguments
        ) {
            val orderId = it.arguments?.getString(OrderDetails.orderIdTypeArg)
            OrderDetailsScreen(modifier = modifier, orderId = orderId)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }