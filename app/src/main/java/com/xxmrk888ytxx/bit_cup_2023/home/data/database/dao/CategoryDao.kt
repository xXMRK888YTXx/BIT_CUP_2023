package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryTable")
    suspend fun getCategory(): List<CategoryEntity>

    @Query("SELECT COUNT(*) > 0 FROM CategoryTable")
    suspend fun isHaveCategories(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)
}