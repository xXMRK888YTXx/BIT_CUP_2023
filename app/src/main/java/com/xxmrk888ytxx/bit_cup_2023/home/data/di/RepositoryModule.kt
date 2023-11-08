package com.xxmrk888ytxx.bit_cup_2023.home.data.di

import com.xxmrk888ytxx.bit_cup_2023.home.data.repository.category.CategoryRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.home.data.repository.curated.CuratedImageRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.category.CategoryRepository
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.curated.CuratedImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    fun bindCuratedImagesRepository(
        curatedImageRepositoryImpl: CuratedImageRepositoryImpl
    ) : CuratedImageRepository
}