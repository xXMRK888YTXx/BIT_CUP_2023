package com.xxmrk888ytxx.bit_cup_2023.detail.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.data.api.PexelsApi
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val pexelsApi: PexelsApi,
) {
    suspend fun loadImage(imageId: Long): ImageDto {
        return pexelsApi.getImage(imageId)
    }
}