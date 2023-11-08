package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated

import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.CuratedImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CuratedImagesLocalDataSource @Inject constructor(
    private val curatedImageDao: CuratedImageDao
) {

    suspend fun getCuratedImages() : List<CuratedImageEntity> = withContext(Dispatchers.IO) {
        curatedImageDao.getCuratedImages()
    }

    suspend fun isHaveCuratedImages() : Boolean = withContext(Dispatchers.IO) {
        curatedImageDao.isHaveCategories()
    }

    suspend fun insertCuratedImage(curatedImageEntity: CuratedImageEntity) = withContext(Dispatchers.IO) {
        curatedImageDao.insert(curatedImageEntity)
    }
}