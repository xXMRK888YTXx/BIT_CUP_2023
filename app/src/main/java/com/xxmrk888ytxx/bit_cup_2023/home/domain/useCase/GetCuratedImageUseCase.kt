package com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.curated.CuratedImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCuratedImageUseCase @Inject constructor(
    private val curatedImageRepository: CuratedImageRepository
) {
    suspend operator fun invoke(): Result<ImageLoadResult> = withContext(Dispatchers.IO) {
        try {
            Result.success(curatedImageRepository.getCuratedImages())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}