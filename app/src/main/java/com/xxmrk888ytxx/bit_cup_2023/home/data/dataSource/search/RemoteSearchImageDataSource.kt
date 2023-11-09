package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search

import com.xxmrk888ytxx.bit_cup_2023.data.api.PexelsApi
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteSearchImageDataSource @Inject constructor(
    private val pexelsApi: PexelsApi,
) {
    suspend fun getImageBySearchQuery(query: String): List<ImageDto> = withContext(Dispatchers.IO) {
        return@withContext pexelsApi.getImagesBySearchQuery(query).images
    }
}