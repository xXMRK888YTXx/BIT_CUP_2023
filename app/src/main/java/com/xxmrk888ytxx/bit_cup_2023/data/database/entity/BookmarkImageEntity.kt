package com.xxmrk888ytxx.bit_cup_2023.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "BookmarkImagesTable",
    indices = [Index("imageId", unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = ImageEntity::class,
            parentColumns = ["id"],
            childColumns = ["imageId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class BookmarkImageEntity(
    @PrimaryKey(autoGenerate = true) val bookmarkId: Int,
    val imageId: Long,
)
