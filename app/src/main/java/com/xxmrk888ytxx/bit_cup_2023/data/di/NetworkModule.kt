package com.xxmrk888ytxx.bit_cup_2023.data.di

import com.xxmrk888ytxx.bit_cup_2023.data.repository.network.NetworkRepositoryImpl
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.network.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    @Singleton
    fun bindNetworkRepository(
        networkRepositoryImpl: NetworkRepositoryImpl
    ) : NetworkRepository
}