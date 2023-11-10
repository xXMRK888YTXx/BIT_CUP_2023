package com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkStateUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {
    operator fun invoke(imageId: Long): Flow<Boolean> =
        bookmarkRepository.isImageBookmarked(imageId)
}