package com.xxmrk888ytxx.bit_cup_2023.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.curated.CuratedImageRepository
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult
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