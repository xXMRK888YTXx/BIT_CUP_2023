package com.xxmrk888ytxx.bit_cup_2023.detail.domain.repository.image

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image

interface ImageRepository {
    suspend fun loadImageFromRemote(imageId: Long): Image
    suspend fun loadImageFromCache(imageId: Long): Image
}