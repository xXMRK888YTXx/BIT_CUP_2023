package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated

import com.xxmrk888ytxx.bit_cup_2023.data.api.PexelsApi
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import javax.inject.Inject

class CuratedImagesRemoteDataSource @Inject constructor(
    private val pexelsApi: PexelsApi
) {
    suspend fun getCuratedImages() : List<ImageDto> {
        return pexelsApi.getCuratedImages().images
    }
}