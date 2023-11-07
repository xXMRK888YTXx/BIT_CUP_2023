package com.xxmrk888ytxx.bit_cup_2023.home.data.repository

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CategoryDto
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.CategoryLocalDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.CategoryRemoteDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryEntity
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.category.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource,
) : CategoryRepository {
    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            if (categoryLocalDataSource.isHaveCategoryInDatabase()) {
                return Result.success(categoryLocalDataSource.getCategory().map { it.toCategory() })
            }

            val categoryFromRemote = categoryRemoteDataSource.getCategories()
            categoryLocalDataSource.insertCategories(categoryFromRemote.map { it.toEntity() })

            Result.success(categoryLocalDataSource.getCategory().map { it.toCategory() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun CategoryEntity.toCategory(): Category {
        return Category(id, title)
    }

    private fun CategoryDto.toEntity(): CategoryEntity {
        return CategoryEntity(id, title)
    }
}