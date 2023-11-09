package com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.detail.domain.repository.image.ImageRepository
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.ImageSourceType
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(imageSourceType: ImageSourceType, imageId: Long): Result<Image> =
        withContext(Dispatchers.IO) {
            try {
                check(imageId > 0)
                Result.success(
                    when (imageSourceType) {
                        ImageSourceType.REMOTE -> imageRepository.loadImageFromRemote(imageId)
                        ImageSourceType.CACHE -> imageRepository.loadImageFromCache(imageId)
                    }
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}