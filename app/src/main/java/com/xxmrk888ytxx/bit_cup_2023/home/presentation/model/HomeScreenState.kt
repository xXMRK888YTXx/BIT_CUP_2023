package com.xxmrk888ytxx.bit_cup_2023.home.presentation.model

import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image

data class HomeScreenState(
    val searchBarScreen: String = "",
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val images: List<Image> = emptyList(),
    val isLoading: Boolean = false,
)
