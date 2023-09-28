package com.pacbittencourt.shop.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.pacbittencourt.shop.R
import com.pacbittencourt.shop.navigation.Cart
import com.pacbittencourt.shop.navigation.OrderDetails
import com.pacbittencourt.shop.navigation.Orders
import com.pacbittencourt.shop.navigation.Products
import com.pacbittencourt.shop.navigation.ShopDestinations
import com.pacbittencourt.shop.navigation.navigateSingleTopTo

@Composable
fun ShopBottomBar(navController: NavHostController, currentScreen: ShopDestinations) {
    BottomAppBar {
        NavigationBarItem(
            label = {
                Text(text = stringResource(R.string.bottom_bar_product))
            },
            selected = currentScreen == Products,
            onClick = {
                navController.navigateSingleTopTo(Products.route)
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = stringResource(R.string.description_items))
            }
        )
        NavigationBarItem(
            label = {
                Text(text = stringResource(R.string.bottom_bar_orders))
            },
            selected = (currentScreen == Orders) || (currentScreen == OrderDetails),
            onClick = {
                navController.navigateSingleTopTo(Orders.route)
            },
            icon = {
                Icon(Icons.Default.List, contentDescription = stringResource(R.string.description_orders))
            }
        )
        NavigationBarItem(
            label = {
                Text(text = stringResource(R.string.bottom_bar_cart))
            },
            selected = currentScreen == Cart,
            onClick = {
                navController.navigateSingleTopTo(Cart.route)
            },
            icon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = stringResource(R.string.description_cart))
            }
        )
    }
}
