package com.xxmrk888ytxx.bit_cup_2023.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResolutionDto(
    @SerialName("original") val original:String,
)
