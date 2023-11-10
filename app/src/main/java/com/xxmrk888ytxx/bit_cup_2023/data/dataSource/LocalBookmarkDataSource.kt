package com.xxmrk888ytxx.bit_cup_2023.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.data.database.dao.BookmarkImageDao
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.BookmarkImageEntity
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkStateChangedAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalBookmarkDataSource @Inject constructor(
    private val bookmarkImageDao: BookmarkImageDao,
) {
    private val _bookmarkStateChangeActions = MutableSharedFlow<BookmarkStateChangedAction>(
        extraBufferCapacity = 1,
    )
    val bookmarkStateChangeActions = _bookmarkStateChangeActions.asSharedFlow()

    suspend fun getBookmarkedImages(): List<ImageEntity> = withContext(Dispatchers.IO) {
        return@withContext bookmarkImageDao.getBookmarkedImages().map { it.image }
    }

    fun isImageBookmarked(imageId: Long): Flow<Boolean> {
        return bookmarkImageDao.isImageBookmarked(imageId)
    }

    suspend fun addImage(imageId: Long) = withContext(Dispatchers.IO) {
        bookmarkImageDao.insertBookmark(BookmarkImageEntity(0, imageId))
        _bookmarkStateChangeActions.tryEmit(BookmarkStateChangedAction(imageId, true))
    }

    suspend fun removeImage(imageId: Long) = withContext(Dispatchers.IO) {
        bookmarkImageDao.removeBookmark(imageId)
        _bookmarkStateChangeActions.tryEmit(BookmarkStateChangedAction(imageId, false))
    }

    suspend fun getImage(imageId: Long): ImageEntity? = withContext(Dispatchers.IO) {
        bookmarkImageDao.getBookmarkedImage(imageId)?.image
    }

}