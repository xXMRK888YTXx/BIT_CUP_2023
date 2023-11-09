package com.xxmrk888ytxx.bit_cup_2023.detail.data.di

import com.xxmrk888ytxx.bit_cup_2023.detail.data.repository.image.ImageRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.repository.image.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl,
    ): ImageRepository
}