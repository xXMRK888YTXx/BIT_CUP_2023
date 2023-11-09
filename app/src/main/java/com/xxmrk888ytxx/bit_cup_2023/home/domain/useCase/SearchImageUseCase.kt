package com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.search.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(
    private val searchImageRepository: SearchImageRepository,
) {
    suspend operator fun invoke(searchQuery: String): Result<ImageLoadResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success(searchImageRepository.getImagesBySearchQuery(searchQuery.trim()))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}