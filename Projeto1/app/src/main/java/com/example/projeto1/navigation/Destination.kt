package com.example.projeto1.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.projeto1.R

sealed class Destination(
    val route: String,
    @StringRes val labelResId: Int,
    val icon: ImageVector
) {
    object Login : Destination(
        route = "login",
        labelResId = R.string.nav_label_login,
        icon = Icons.Default.Lock
    )

    object Explore : Destination(
        route = "explore",
        labelResId = R.string.nav_label_explore,
        icon = Icons.Default.Explore
    )

    object MyExchanges : Destination(
        route = "my_exchanges",
        labelResId = R.string.nav_label_my_exchanges,
        icon = Icons.Default.SwapHoriz
    )

    object Add : Destination(
        route = "add",
        labelResId = R.string.nav_label_add,
        icon = Icons.Default.Add
    )

    companion object {
        val bottomBarDestinations = listOf(Explore, MyExchanges, Add)
    }
}