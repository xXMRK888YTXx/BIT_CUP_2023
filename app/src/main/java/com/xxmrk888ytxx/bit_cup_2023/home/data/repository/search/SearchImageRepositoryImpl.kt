package com.xxmrk888ytxx.bit_cup_2023.home.data.repository.search

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search.LocalSearchImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search.RemoteSearchImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.search.SearchImageRepository
import javax.inject.Inject

class SearchImageRepositoryImpl @Inject constructor(
    private val localSearchImageDataSource: LocalSearchImageDataSource,
    private val remoteSearchImageDataSource: RemoteSearchImageDataSource,
) : SearchImageRepository {
    override suspend fun getImagesBySearchQuery(query: String): ImageLoadResult {
        val imagesFromDatabase = localSearchImageDataSource.getImagesBySearchQuery(query)

        if (imagesFromDatabase.isNotEmpty()) {
            return ImageLoadResult(
                images = imagesFromDatabase.map { it.toImage() },
                isFromCache = true
            )
        }

        val imageFromRemote = remoteSearchImageDataSource.getImageBySearchQuery(query)

        localSearchImageDataSource.addImages(
            query = query,
            images = imageFromRemote.map { it.toEntity() }
        )

        return ImageLoadResult(
            images = localSearchImageDataSource.getImagesBySearchQuery(query).map { it.toImage() },
            isFromCache = false
        )
    }

    private fun ImageDto.toEntity(): ImageEntity {
        return ImageEntity(id, imageName, imageResolutions.original, author)
    }

    private fun ImageEntity.toImage(): Image {
        return Image(id, name, imageUrl, author)
    }
}