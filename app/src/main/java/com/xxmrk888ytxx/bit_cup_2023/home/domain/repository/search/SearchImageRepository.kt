package com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.search

import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image

interface SearchImageRepository {
    suspend fun getImagesBySearchQuery(query: String): List<Image>
}