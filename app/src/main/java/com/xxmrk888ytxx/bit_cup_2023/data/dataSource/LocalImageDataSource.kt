package com.xxmrk888ytxx.bit_cup_2023.data.dataSource

import com.xxmrk888ytxx.bit_cup_2023.data.database.dao.ImageDao
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalImageDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {
    suspend fun addImage(imageEntity: ImageEntity) = withContext(Dispatchers.IO) {
        imageDao.insert(imageEntity)
    }

    suspend fun getImage(id: Long) = withContext(Dispatchers.IO) {
        imageDao.getImage(id)
    }
}