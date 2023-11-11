package com.xxmrk888ytxx.bit_cup_2023.domain.repository.category

import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}