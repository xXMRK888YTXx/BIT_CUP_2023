package com.xxmrk888ytxx.bit_cup_2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.BookmarkImageEntity
import com.xxmrk888ytxx.bit_cup_2023.data.database.model.BookmarkImage
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkImageDao {

    @Query("SELECT * FROM BookmarkImagesTable")
    @Transaction
    suspend fun getBookmarkedImages(): List<BookmarkImage>

    @Query("SELECT * FROM BookmarkImagesTable WHERE imageId = :imageId")
    @Transaction
    suspend fun getBookmarkedImage(imageId: Long): BookmarkImage?

    @Query("SELECT COUNT(*) > 0 FROM BookmarkImagesTable WHERE imageId = :imageId")
    fun isImageBookmarked(imageId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkImageEntity: BookmarkImageEntity)

    @Query("DELETE FROM BookmarkImagesTable WHERE imageId = :imageId")
    suspend fun removeBookmark(imageId: Long)
}