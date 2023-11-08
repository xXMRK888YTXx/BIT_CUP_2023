package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity

@Dao
interface CuratedImageDao {
    @Query("SELECT * FROM CuratedImagesTable")
    suspend fun getCuratedImages(): List<CuratedImageEntity>

    @Query("SELECT COUNT(*) > 0 FROM CuratedImagesTable")
    suspend fun isHaveCategories(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CuratedImageEntity)
}