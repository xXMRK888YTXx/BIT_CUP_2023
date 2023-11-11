package com.xxmrk888ytxx.bit_cup_2023.home.data.repository.curated

import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.curated.CuratedImageRepository
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated.CuratedImagesLocalDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated.CuratedImagesRemoteDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.ImageLoadResult
import javax.inject.Inject

class CuratedImageRepositoryImpl @Inject constructor(
    private val curatedImagesLocalDataSource: CuratedImagesLocalDataSource,
    private val curatedImagesRemoteDataSource: CuratedImagesRemoteDataSource,
) : CuratedImageRepository {
    override suspend fun getCuratedImages(): ImageLoadResult {
        val curatedImagesFromCache = curatedImagesLocalDataSource.getCuratedImages()
        if (curatedImagesFromCache.isNotEmpty()) {
            return ImageLoadResult(
                images = curatedImagesLocalDataSource.getCuratedImages().map { it.toImage() },
                isFromCache = true
            )
        }

        val curatedImagesFromRemote = curatedImagesRemoteDataSource.getCuratedImages()

        curatedImagesFromRemote.forEach {
            curatedImagesLocalDataSource.insertCuratedImage(it.toEntity())
        }

        return ImageLoadResult(
            images = curatedImagesLocalDataSource.getCuratedImages().map { it.toImage() },
            isFromCache = false
        )
    }

    private fun ImageEntity.toImage(): Image {
        return Image(id, name, imageUrl, author)
    }

    private fun ImageDto.toEntity(): ImageEntity {
        return ImageEntity(id, imageName, imageResolutions.original, author)
    }
}