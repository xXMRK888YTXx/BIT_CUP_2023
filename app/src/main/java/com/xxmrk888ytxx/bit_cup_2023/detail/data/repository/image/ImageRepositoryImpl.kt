package com.xxmrk888ytxx.bit_cup_2023.detail.data.repository.image

import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.data.dataSource.LocalImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.detail.data.dataSource.RemoteImageDataSource
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.exception.ImageNotFoundException
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.repository.image.ImageRepository
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.ImageEntity
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val localImageDataSource: LocalImageDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
) : ImageRepository {
    override suspend fun loadImageFromRemote(imageId: Long): Image {
        return remoteImageDataSource.loadImage(imageId).toImage()
    }

    override suspend fun loadImageFromCache(imageId: Long): Image {
        return localImageDataSource.getImage(imageId)?.toImage() ?: throw ImageNotFoundException()
    }

    private fun ImageDto.toImage(): Image {
        return Image(id, imageName, imageResolutions.original, author)
    }

    private fun ImageEntity.toImage(): Image {
        return Image(id, name, imageUrl, author)
    }
}