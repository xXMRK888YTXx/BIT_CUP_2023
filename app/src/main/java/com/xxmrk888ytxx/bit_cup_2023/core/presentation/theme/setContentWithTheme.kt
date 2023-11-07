package com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

fun ComponentActivity.setContentWithTheme(
    content: @Composable () -> Unit,
) {
    setContent {
        val themeColors = if (isSystemInDarkTheme()) Theme.dark else Theme.light
        CompositionLocalProvider(
            LocalTheme provides themeColors,
            content = content
        )
    }
}