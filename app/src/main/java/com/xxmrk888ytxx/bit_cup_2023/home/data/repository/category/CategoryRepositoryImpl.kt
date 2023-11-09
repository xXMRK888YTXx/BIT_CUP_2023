package com.xxmrk888ytxx.bit_cup_2023.home.data.repository.category

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CategoryDto
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.category.CategoryLocalDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.category.CategoryRemoteDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CategoryEntity
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.category.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
        if (categoryLocalDataSource.isHaveCategoryInDatabase()) {
            return@withContext categoryLocalDataSource.getCategory().map { it.toCategory() }
        }

        val categoryFromRemote = categoryRemoteDataSource.getCategories()
        categoryLocalDataSource.insertCategories(categoryFromRemote.map { it.toEntity() })

        return@withContext categoryLocalDataSource.getCategory().map { it.toCategory() }
    }

    private fun CategoryEntity.toCategory(): Category {
        return Category(id, title)
    }

    private fun CategoryDto.toEntity(): CategoryEntity {
        return CategoryEntity(id, title)
    }
}