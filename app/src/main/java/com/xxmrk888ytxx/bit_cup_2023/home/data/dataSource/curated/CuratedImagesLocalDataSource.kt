package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated

import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.CuratedImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.ImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CuratedImagesLocalDataSource @Inject constructor(
    private val curatedImageDao: CuratedImageDao,
    private val imageDao: ImageDao,
) {

    suspend fun getCuratedImages(): List<ImageEntity> = withContext(Dispatchers.IO) {
        curatedImageDao.getCuratedImages().map { it.imageEntity }
    }

    suspend fun insertCuratedImage(imageEntity: ImageEntity) = withContext(Dispatchers.IO) {
        imageDao.insert(imageEntity)
        curatedImageDao.insert(CuratedImageEntity(imageEntity.id))
    }
}