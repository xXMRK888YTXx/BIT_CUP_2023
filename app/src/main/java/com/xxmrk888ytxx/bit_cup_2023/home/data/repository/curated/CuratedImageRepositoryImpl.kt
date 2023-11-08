package com.xxmrk888ytxx.bit_cup_2023.home.data.repository.curated

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated.CuratedImagesLocalDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.curated.CuratedImagesRemoteDataSource
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.curated.CuratedImageRepository
import javax.inject.Inject

class CuratedImageRepositoryImpl @Inject constructor(
    private val curatedImagesLocalDataSource: CuratedImagesLocalDataSource,
    private val curatedImagesRemoteDataSource: CuratedImagesRemoteDataSource
): CuratedImageRepository {
    override suspend fun getCuratedImages(): List<Image> {
        if(curatedImagesLocalDataSource.isHaveCuratedImages()) {
            return curatedImagesLocalDataSource.getCuratedImages().map { it.toImage() }
        }

        val curatedImagesFromRemote = curatedImagesRemoteDataSource.getCuratedImages()

        curatedImagesFromRemote.forEach {
            curatedImagesLocalDataSource.insertCuratedImage(it.toEntity())
        }

        return curatedImagesLocalDataSource.getCuratedImages().map { it.toImage() }
    }

    private fun CuratedImageEntity.toImage() : Image {
        return Image(id, name, imageUrl)
    }

    private fun ImageDto.toEntity() : CuratedImageEntity {
        return CuratedImageEntity(id,imageName,imageResolutions.original)
    }
}