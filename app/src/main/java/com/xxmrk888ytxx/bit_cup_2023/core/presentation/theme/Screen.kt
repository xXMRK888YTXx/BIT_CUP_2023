package com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme

sealed class Screen(val route:String) {
    object Splash : Screen("Splash")

    object Home : Screen("Home")
}
