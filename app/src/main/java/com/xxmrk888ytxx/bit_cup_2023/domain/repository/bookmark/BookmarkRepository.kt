package com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    val bookmarkStateChangeActions: Flow<BookmarkStateChangedAction>
    suspend fun getBookmarkedImages(): List<Image>
    fun isImageBookmarked(imageId: Long): Flow<Boolean>
    suspend fun addImage(imageId: Long)
    suspend fun removeImage(imageId: Long)
    suspend fun getImage(imageId: Long): Image?
}