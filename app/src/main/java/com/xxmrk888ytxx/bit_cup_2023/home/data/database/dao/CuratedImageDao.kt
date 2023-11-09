package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.model.CuratedImage

@Dao
interface CuratedImageDao {
    @Query("SELECT * FROM CURATEDIMAGETABLE")
    @Transaction
    suspend fun getCuratedImages(): List<CuratedImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(curatedImageEntity: CuratedImageEntity)
}