package com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.SearchImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.model.SearchImage

@Dao
interface SearchImageDao {
    @Query("SELECT * FROM SEARCHIMAGETABLE WHERE searchQuery LIKE :query")
    @Transaction
    suspend fun getSearchImages(query: String): List<SearchImage>

    @Insert
    suspend fun insert(searchImageEntity: SearchImageEntity)
}