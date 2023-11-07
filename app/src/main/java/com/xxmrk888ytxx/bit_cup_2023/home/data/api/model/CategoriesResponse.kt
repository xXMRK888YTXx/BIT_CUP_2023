package com.xxmrk888ytxx.bit_cup_2023.home.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    @SerialName("collections") val categories: List<CategoryDto>,
)
