package com.xxmrk888ytxx.bit_cup_2023.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.data.database.dao.BookmarkImageDao
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.BookmarkImageEntity
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalBookmarkDataSource @Inject constructor(
    private val bookmarkImageDao: BookmarkImageDao,
) {
    suspend fun getBookmarkedImages(): List<ImageEntity> = withContext(Dispatchers.IO) {
        return@withContext bookmarkImageDao.getBookmarkedImages().map { it.image }
    }

    fun isImageBookmarked(imageId: Long): Flow<Boolean> {
        return bookmarkImageDao.isImageBookmarked(imageId)
    }

    suspend fun addImage(imageId: Long) = withContext(Dispatchers.IO) {
        bookmarkImageDao.insertBookmark(BookmarkImageEntity(0, imageId))
    }

    suspend fun removeImage(imageId: Long) = withContext(Dispatchers.IO) {
        bookmarkImageDao.removeBookmark(imageId)
    }

}