package com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "SearchImageTable",
    indices = [Index("searchQuery", unique = false), Index("imageId", unique = false)],
    foreignKeys = [
        ForeignKey(
            entity = ImageEntity::class,
            parentColumns = ["id"],
            childColumns = ["imageId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class SearchImageEntity(
    @PrimaryKey(autoGenerate = true) val searchImageId: Int = 0,
    val searchQuery: String,
    val imageId: Long,
)