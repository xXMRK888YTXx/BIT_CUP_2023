package com.xxmrk888ytxx.bit_cup_2023.home.presentation.model

import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category

data class HomeScreenState(
    val searchBarScreen: String = "",
    val categories: List<Category> = emptyList(),
)
