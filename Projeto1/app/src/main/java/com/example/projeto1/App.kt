package com.example.projeto1

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projeto1.ui.screen.LoginScreen
import com.example.projeto1.ui.screens.TradesScreen
import kotlinx.coroutines.launch
import com.example.projeto1.ui.theme.Projeto1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun App() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(stringResource(R.string.app_title)) },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            )
//        },
//        modifier = Modifier.fillMaxSize()
//    ) { innerPadding ->
//        LoginScreen(
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun App(
    navController : NavHostController = rememberNavController(),
    startingRoute : String = "login"
) {
    // here
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startingRoute
        ) {
            composable("login") {
                LoginScreen(
                    onSuccessfulLogin = {
                        // here
                        navController.navigate("main") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    },
                    navigateUp = {
                        scope.launch {
                            activity?.finish()
                        }
                    },
                )
            }
            composable("main") {
                //TradesScreen(

                //)
            }
            composable("my_trades") {

            }
            composable("register_trade") {

            }
            composable("trade_details") {

            }
            composable("respond_trade") {

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Projeto1Theme {
        App()
    }
}