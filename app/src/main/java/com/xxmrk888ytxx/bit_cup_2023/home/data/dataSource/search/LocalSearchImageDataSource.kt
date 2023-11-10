package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search

import com.xxmrk888ytxx.bit_cup_2023.data.dataSource.LocalImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.SearchImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.SearchImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalSearchImageDataSource @Inject constructor(
    private val searchImageDao: SearchImageDao,
    private val localImageDataSource: LocalImageDataSource,
) {

    suspend fun addImages(query: String, images: List<ImageEntity>) = withContext(Dispatchers.IO) {
        images.forEach {
            localImageDataSource.addImage(it)
            searchImageDao.insert(
                SearchImageEntity(
                    searchImageId = 0,
                    searchQuery = query,
                    imageId = it.id
                )
            )
        }
    }

    suspend fun getImagesBySearchQuery(query: String): List<ImageEntity> =
        withContext(Dispatchers.IO) {
            searchImageDao.getSearchImages(query).map { it.imageEntity }
        }
}