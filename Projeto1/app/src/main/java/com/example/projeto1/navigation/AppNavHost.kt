package com.example.projeto1.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projeto1.repository.SavedLoginRepository
import com.example.projeto1.repository.TrocasRepository
import com.example.projeto1.repository.room.AppDatabase
import com.example.projeto1.ui.screen.AddTradeScreen
import com.example.projeto1.ui.screen.LoginScreen
import com.example.projeto1.ui.screen.LoginScreenWithNavigation
import com.example.projeto1.ui.screens.MyTradeDetailsScreen
import com.example.projeto1.ui.screens.TradeDetailsScreen
import com.example.projeto1.ui.screens.TradesScreen
import com.example.projeto1.ui.screens.MyTradesScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Destination.Login.route,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
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
            val savedLoginRepository = SavedLoginRepository(
                AppDatabase.getDatabase(application).savedLoginDao()
            )

            TradesScreen(
                application = application,
                repository = repository,
                savedLoginRepository = savedLoginRepository,
                onTrocaClick = { exchangeId ->
                    navController.navigate("trade_details/$exchangeId")
                },
                onLogout = {
                    navController.navigate(Destination.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            ) {
                scope.launch {
                    navController.popBackStack(Destination.Login.route, inclusive = false)
                }
            }

        }
        composable(Destination.MyExchanges.route) {
            MyTradesScreen(
                application = LocalContext.current.applicationContext as Application,
                repository = TrocasRepository(),
                onTrocaClick = { exchangeId ->
                    navController.navigate("my_trade_details/$exchangeId")
                })
        }
        composable(Destination.Add.route) {
            val application = LocalContext.current.applicationContext as Application
            val repository = TrocasRepository()

            AddTradeScreen(application = application)
        }

        composable(
            route = "trade_details/{exchangeId}",
            arguments = listOf(navArgument("exchangeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val application = LocalContext.current.applicationContext as Application
            val repository = TrocasRepository()

            val exchangeId = backStackEntry.arguments?.getInt("exchangeId") ?: 0

            TradeDetailsScreen(
                exchangeId = exchangeId,
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "my_trade_details/{exchangeId}",
            arguments = listOf(navArgument("exchangeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val application = LocalContext.current.applicationContext as Application
            val repository = TrocasRepository()

            val exchangeId = backStackEntry.arguments?.getInt("exchangeId") ?: 0

            MyTradeDetailsScreen(
                exchangeId = exchangeId,
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
