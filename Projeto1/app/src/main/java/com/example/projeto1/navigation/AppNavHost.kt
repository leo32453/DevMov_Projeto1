package com.example.projeto1.navigation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projeto1.ui.screen.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Destination.Login.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Destination.Login.route) {
            LoginScreenWithNavigation(navController = navController)
        }
        composable(Destination.Explore.route) {
            MainScreen()
        }
        composable(Destination.MyExchanges.route) {
            MyTradesScreen()
        }
        composable(Destination.Add.route) {
            AddTradeScreen()
        }
    }
}