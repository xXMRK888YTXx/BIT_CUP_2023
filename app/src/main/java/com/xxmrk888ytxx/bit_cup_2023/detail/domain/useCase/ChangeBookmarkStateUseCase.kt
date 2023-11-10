package com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

class ChangeBookmarkStateUseCase @Inject constructor(
    private val bookMarkRepository: BookmarkRepository,
) {

    suspend operator fun invoke(imageId: Long, bookMarkImage: Boolean) {
        if (bookMarkImage) {
            bookMarkRepository.addImage(imageId)
        } else {
            bookMarkRepository.removeImage(imageId)
        }
    }
}