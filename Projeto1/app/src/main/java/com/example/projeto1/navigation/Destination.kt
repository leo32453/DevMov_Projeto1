package com.example.projeto1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.*

sealed class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Login : Destination(
        route = "login",
        label = "Login",
        icon = Icons.Default.Lock
    )

    object Explore : Destination(
        route = "explore",
        label = "Explorar",
        icon = Icons.Default.Explore
    )

    object MyExchanges : Destination(
        route = "my_exchanges",
        label = "Minhas Trocas",
        icon = Icons.Default.SwapHoriz
    )

    object Add : Destination(
        route = "add",
        label = "Adicionar",
        icon = Icons.Default.Add
    )

    companion object {
        // Lista apenas dos destinos que aparecem na BottomBar
        val bottomBarDestinations = listOf(Explore, MyExchanges, Add)
    }
}