package com.xxmrk888ytxx.bit_cup_2023.home.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.SearchImageEntity

data class SearchImage(
    @Embedded val searchImageEntity: SearchImageEntity,
    @Relation(
        parentColumn = "imageId",
        entityColumn = "id"
    )
    val imageEntity: ImageEntity,
)
