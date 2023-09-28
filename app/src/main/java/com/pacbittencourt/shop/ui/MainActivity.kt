package com.pacbittencourt.shop.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pacbittencourt.shop.navigation.Products
import com.pacbittencourt.shop.navigation.ShopNavHost
import com.pacbittencourt.shop.navigation.shopDestinations
import com.pacbittencourt.shop.ui.components.ShopBottomBar
import com.pacbittencourt.shop.ui.theme.ShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopApp() {
    ShopTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            shopDestinations.find { it.route == currentDestination?.route } ?: Products
        Scaffold(
            bottomBar = {
                ShopBottomBar(navController, currentScreen)
            }
        ) { innerPadding ->
            ShopNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
