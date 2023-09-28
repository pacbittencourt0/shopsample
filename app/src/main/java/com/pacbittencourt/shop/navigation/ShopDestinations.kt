package com.pacbittencourt.shop.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface ShopDestinations {
    val route: String
}

object Cart : ShopDestinations {
    override val route: String = "cart"
}

object Orders : ShopDestinations {
    override val route: String = "orders"
}

object OrderDetails : ShopDestinations {
    const val orderIdTypeArg = "order_id"
    const val simpleRoute = "order_details"
    override val route: String = "$simpleRoute/{$orderIdTypeArg}"
    val arguments = listOf(
        navArgument(orderIdTypeArg) { type = NavType.StringType }
    )
}

object Products : ShopDestinations {
    override val route: String = "products"
}

val shopDestinations = listOf(Cart, Orders, Products, OrderDetails)