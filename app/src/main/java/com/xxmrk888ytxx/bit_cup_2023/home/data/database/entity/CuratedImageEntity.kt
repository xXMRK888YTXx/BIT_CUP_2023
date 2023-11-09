package com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "CuratedImageTable",
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
data class CuratedImageEntity(
    @PrimaryKey val imageId: Long,
)
