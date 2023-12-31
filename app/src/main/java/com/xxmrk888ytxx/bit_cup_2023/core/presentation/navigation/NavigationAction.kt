package com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation

import android.os.Bundle
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen

sealed interface NavigationAction {
    class Navigate(
        val screen: Screen,
        val popTo: Screen? = null,
        val args: Bundle? = null,
    ) : NavigationAction

    object NavigateUp : NavigationAction
}