package com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "CategoryTable",
    indices = [Index("id", unique = true)]
)
data class CategoryEntity(
    @PrimaryKey val id: String,
    val title: String,
)