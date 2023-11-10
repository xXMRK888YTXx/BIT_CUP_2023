package com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class GetBookmarkStateChangesUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) {

    operator fun invoke() = bookmarkRepository.bookmarkStateChangeActions
}