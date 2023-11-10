package com.xxmrk888ytxx.bit_cup_2023.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.BookmarkImageEntity
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity

data class BookmarkImage(
    @Embedded val bookmarkImage: BookmarkImageEntity,
    @Relation(
        parentColumn = "imageId",
        entityColumn = "id"
    )
    val image: ImageEntity,
)
