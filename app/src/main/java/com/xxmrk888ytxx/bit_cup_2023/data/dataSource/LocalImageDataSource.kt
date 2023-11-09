package com.xxmrk888ytxx.bit_cup_2023.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.ImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalImageDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {
    suspend fun addImage(imageEntity: ImageEntity) = withContext(Dispatchers.IO) {
        imageDao.insert(imageEntity)
    }
}