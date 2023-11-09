package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageEntity: ImageEntity)

    @Query("SELECT * FROM IMAGETABLE WHERE id = :id LIMIT 1")
    suspend fun getImage(id: Long): ImageEntity?
}