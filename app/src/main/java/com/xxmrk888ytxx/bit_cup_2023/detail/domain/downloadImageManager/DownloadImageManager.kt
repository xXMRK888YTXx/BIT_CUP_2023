package com.xxmrk888ytxx.bit_cup_2023.detail.domain.downloadImageManager

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image

interface DownloadImageManager {
    suspend fun downloadImageInDownloadDir(image: Image)
}