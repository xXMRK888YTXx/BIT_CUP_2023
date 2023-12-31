package com.xxmrk888ytxx.bit_cup_2023.data.repository.bookmark

import com.xxmrk888ytxx.bit_cup_2023.data.dataSource.LocalBookmarkDataSource
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkStateChangedAction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val localBookmarkDataSource: LocalBookmarkDataSource,
) : BookmarkRepository {
    override val bookmarkStateChangeActions: Flow<BookmarkStateChangedAction> =
        localBookmarkDataSource.bookmarkStateChangeActions

    override suspend fun getBookmarkedImages(): List<Image> {
        return localBookmarkDataSource.getBookmarkedImages().map { it.toImage() }
    }

    override fun isImageBookmarked(imageId: Long): Flow<Boolean> {
        return localBookmarkDataSource.isImageBookmarked(imageId)
    }

    override suspend fun addImage(imageId: Long) {
        localBookmarkDataSource.addImage(imageId)
    }

    override suspend fun removeImage(imageId: Long) {
        localBookmarkDataSource.removeImage(imageId)
    }

    override suspend fun getImage(imageId: Long): Image? =
        localBookmarkDataSource.getImage(imageId)?.toImage()

    private fun ImageEntity.toImage(): Image {
        return Image(id, name, imageUrl, author)
    }
}