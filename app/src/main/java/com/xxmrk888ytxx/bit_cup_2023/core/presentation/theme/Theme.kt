package com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme

import androidx.compose.ui.graphics.Color

data class Theme(
    val red: Color,
    val white: Color,
    val gray: Color,
    val darkGray: Color,
    val black: Color,
) {
    companion object {
        val light = Theme(
            red = Color(0xFFBB1020),
            white = Color(0xFFFFFFFF),
            gray = Color(0xFFF3F5F9),
            darkGray = Color(0xFF868686),
            black = Color(0xFF1E1E1E)
        )

        val dark = Theme(
            red = Color(0xFFBB1020),
            white = Color(0xFFFFFFFF),
            gray = Color(0xFF393939),
            darkGray = Color(0xFFB5B5B5),
            black = Color(0xFF1E1E1E)
        )
    }
}
