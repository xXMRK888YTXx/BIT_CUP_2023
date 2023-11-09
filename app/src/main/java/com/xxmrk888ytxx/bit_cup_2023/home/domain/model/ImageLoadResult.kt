package com.xxmrk888ytxx.bit_cup_2023.home.domain.model

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image

data class ImageLoadResult(
    val images: List<Image>,
    val isFromCache: Boolean,
)
