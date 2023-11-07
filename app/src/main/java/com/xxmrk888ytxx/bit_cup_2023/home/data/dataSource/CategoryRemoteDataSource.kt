package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.CategoriesApi
import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CategoryDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
    private val categoriesApi: CategoriesApi,
) {
    suspend fun getCategories(): List<CategoryDto> =
        withContext(Dispatchers.IO) { categoriesApi.getCategories().categories }
}