package com.xxmrk888ytxx.bit_cup_2023.home.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    @SerialName("id") val id: Long,
    @SerialName("alt") val imageName: String,
    @SerialName("src") val imageResolutions: ImageResolutionDto,
)
