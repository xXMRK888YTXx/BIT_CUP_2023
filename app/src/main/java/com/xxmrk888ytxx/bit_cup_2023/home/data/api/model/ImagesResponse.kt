package com.xxmrk888ytxx.bit_cup_2023.home.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("photos") val images: List<ImageDto>,
)
