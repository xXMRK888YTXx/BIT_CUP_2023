package com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.category.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            Result.success(categoryRepository.getCategories())
        }catch (e:Exception) {
            Result.failure(e)
        }
    }
}