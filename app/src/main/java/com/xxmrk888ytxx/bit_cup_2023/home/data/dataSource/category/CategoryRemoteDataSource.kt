package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.category

import com.xxmrk888ytxx.bit_cup_2023.data.api.PexelsApi
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.CategoryDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
    private val pexelsApi: PexelsApi,
) {
    suspend fun getCategories(): List<CategoryDto> =
        withContext(Dispatchers.IO) { pexelsApi.getCategories().categories }
}