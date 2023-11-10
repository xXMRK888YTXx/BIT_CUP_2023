package com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBookmarkedImageUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    suspend operator fun invoke(imageId: Long): Result<Image> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.success(bookmarkRepository.getImage(imageId)!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
