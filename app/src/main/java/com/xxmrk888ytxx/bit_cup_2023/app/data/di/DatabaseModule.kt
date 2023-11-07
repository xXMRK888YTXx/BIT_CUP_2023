package com.xxmrk888ytxx.bit_cup_2023.app.data.di

import android.content.Context
import androidx.room.Room
import com.xxmrk888ytxx.bit_cup_2023.app.data.AppDatabase
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.getCategoryDao()
    }

    companion object {
        const val DATABASE_NAME = "database"
    }
}