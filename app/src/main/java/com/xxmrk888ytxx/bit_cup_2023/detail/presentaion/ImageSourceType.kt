package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

enum class ImageSourceType(val id: Int) {
    REMOTE(0),
    CACHE(1),
}

fun Int.toImageSourceType(): ImageSourceType {
    return ImageSourceType.entries.first { it.id == this }
}