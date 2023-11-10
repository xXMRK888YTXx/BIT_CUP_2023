package com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme

sealed class Screen(val route:String) {
    object Splash : Screen("Splash")

    object Home : Screen("Home")

    object Details : Screen("Details") {
        const val IMAGE_ID_NAVIGATION_ARGUMENT_KEY = "IMAGE_ID_NAVIGATION_ARGUMENT_KEY"
        const val IMAGE_SOURCE_ARGUMENT_KEY = "IMAGE_SOURCE_ARGUMENT_KEY"
    }

    object Bookmark : Screen("Bookmark")

    companion object {
        const val ARGUMENT_BUNDLE_KEY = "ARGUMENT_BUNDLE_KEY"
    }
}
