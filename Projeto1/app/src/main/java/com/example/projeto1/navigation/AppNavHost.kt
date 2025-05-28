package com.example.projeto1.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.ui.screen.AddTradeScreen
import com.example.projeto1.ui.screen.LoginScreenWithNavigation
import com.example.projeto1.ui.screen.MyTradesScreen
import com.example.projeto1.ui.screens.TradesScreen

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
            val application = LocalContext.current.applicationContext as Application
            val repository = TrocasRepository()

            TradesScreen(application = application, repository = repository)
        }
        composable(Destination.MyExchanges.route) {
            MyTradesScreen()
        }
        composable(Destination.Add.route) {
            AddTradeScreen()
        }
    }
}
