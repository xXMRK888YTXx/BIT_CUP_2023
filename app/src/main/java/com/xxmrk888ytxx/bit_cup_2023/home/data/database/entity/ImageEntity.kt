package com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ImageTable",
    indices = [Index("id", unique = true)]
)
data class ImageEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String,
)
