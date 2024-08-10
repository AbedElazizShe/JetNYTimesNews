package com.example.jetnytimesnews.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen(route = "home")
    data object SavedForLater : Screen(route = "saved_for_later")
}
