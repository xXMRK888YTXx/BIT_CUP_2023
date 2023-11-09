package com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.curated

import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.ImageLoadResult

interface CuratedImageRepository {
    suspend fun getCuratedImages(): ImageLoadResult
}