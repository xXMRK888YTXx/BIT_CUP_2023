package com.xxmrk888ytxx.bit_cup_2023.detail.data.di

import com.xxmrk888ytxx.bit_cup_2023.detail.data.downloadImageManager.DownloadImageManagerImpl
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.downloadImageManager.DownloadImageManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DownloadImageManagerModule {
    @Binds
    @Singleton
    fun bindDownloadImageManager(
        downloadImageManagerImpl: DownloadImageManagerImpl,
    ): DownloadImageManager
}