package com.xxmrk888ytxx.bit_cup_2023.home.data.di

import com.xxmrk888ytxx.bit_cup_2023.home.data.repository.CategoryRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.home.domain.repository.category.CategoryRepository
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
}