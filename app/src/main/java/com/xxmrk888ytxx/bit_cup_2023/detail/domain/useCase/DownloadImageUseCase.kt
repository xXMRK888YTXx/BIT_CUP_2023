package com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.detail.domain.downloadImageManager.DownloadImageManager
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    private val downloadImageManager: DownloadImageManager,
) {
    suspend operator fun invoke(image: Image) =
        downloadImageManager.downloadImageInDownloadDir(image)
}