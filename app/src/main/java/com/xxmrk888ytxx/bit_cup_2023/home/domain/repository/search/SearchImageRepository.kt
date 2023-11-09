package com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.search

import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult

interface SearchImageRepository {
    suspend fun getImagesBySearchQuery(query: String): ImageLoadResult
}