package com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme

import androidx.compose.ui.graphics.Color

data class Theme(
    val splashScreenBackground: Color,
    val background: Color,
    val searchBar: Color,
    val searchBarPlaceholder: Color,
    val selectedCategory:Color,
    val nonSelectedCategory: Color,
    val selectedCategoryTextColor:Color,
    val nonSelectedCategoryTextColor:Color,
)

val lightTheme = Theme(
    splashScreenBackground = lightColors.red,
    background = lightColors.white,
    searchBar = lightColors.gray,
    searchBarPlaceholder = lightColors.darkGray,
    selectedCategory = lightColors.red,
    nonSelectedCategory = lightColors.gray,
    selectedCategoryTextColor = lightColors.white,
    nonSelectedCategoryTextColor = lightColors.black,
)

val darkTheme = Theme(
    splashScreenBackground = darkColors.red,
    background = darkColors.black,
    searchBar = darkColors.gray,
    searchBarPlaceholder = darkColors.darkGray,
    selectedCategory = darkColors.red,
    nonSelectedCategory = darkColors.gray,
    selectedCategoryTextColor = darkColors.white,
    nonSelectedCategoryTextColor = darkColors.white,
)
