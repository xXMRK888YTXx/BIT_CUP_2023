package com.xxmrk888ytxx.bit_cup_2023.data.di

import com.xxmrk888ytxx.bit_cup_2023.data.repository.bookmark.BookmarkRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.bookmark.BookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindBookmarkImageRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl,
    ): BookmarkRepository
}