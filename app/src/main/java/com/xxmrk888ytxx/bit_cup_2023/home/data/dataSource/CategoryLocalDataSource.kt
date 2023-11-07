package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
) {
    suspend fun getCategory(): List<CategoryEntity> = withContext(Dispatchers.IO) {
        categoryDao.getCategory()
    }

    suspend fun isHaveCategoryInDatabase(): Boolean = withContext(Dispatchers.IO) {
        categoryDao.isHaveCategories()
    }

    suspend fun insertCategories(categories: List<CategoryEntity>) = withContext(Dispatchers.IO) {
        categories.forEach { categoryDao.insert(it) }
    }
}