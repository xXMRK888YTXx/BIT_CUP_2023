package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetupNavigationAndStatusBarColor(
    color: Color,
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = Unit, block = {
        systemUiController.setStatusBarColor(color)
        systemUiController.setNavigationBarColor(color)
    })
}