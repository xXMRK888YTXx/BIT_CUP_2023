package com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark

data class BookmarkStateChangedAction(
    val imageId: Long,
    val isAddedToBookmark: Boolean,
)
