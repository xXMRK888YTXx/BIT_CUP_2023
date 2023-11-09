package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageEntity: ImageEntity)
}