package com.xxmrk888ytxx.bit_cup_2023.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.category.CategoryRepository
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.Category
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