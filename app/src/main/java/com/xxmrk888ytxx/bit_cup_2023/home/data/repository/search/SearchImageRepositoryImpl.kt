package com.xxmrk888ytxx.bit_cup_2023.home.data.repository.search

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search.LocalSearchImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search.RemoteSearchImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.search.SearchImageRepository
import javax.inject.Inject

class SearchImageRepositoryImpl @Inject constructor(
    private val localSearchImageDataSource: LocalSearchImageDataSource,
    private val remoteSearchImageDataSource: RemoteSearchImageDataSource,
) : SearchImageRepository {
    override suspend fun getImagesBySearchQuery(query: String): List<Image> {
        val images = localSearchImageDataSource.getImagesBySearchQuery(query)
            ?: remoteSearchImageDataSource.getImageBySearchQuery(query).also {
                localSearchImageDataSource.addImages(query, it)
            }

        return images.map { it.toImage() }
    }

    private fun ImageDto.toImage(): Image {
        return Image(id, imageName, imageResolutions.original)
    }
}