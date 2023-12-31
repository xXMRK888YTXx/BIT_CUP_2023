package com.xxmrk888ytxx.bit_cup_2023.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.bit_cup_2023.data.database.dao.BookmarkImageDao
import com.xxmrk888ytxx.bit_cup_2023.data.database.dao.ImageDao
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.BookmarkImageEntity
import com.xxmrk888ytxx.bit_cup_2023.data.database.entity.ImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.CategoryDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.CuratedImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.dao.SearchImageDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CategoryEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.CuratedImageEntity
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.entity.SearchImageEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        ImageEntity::class,
        CuratedImageEntity::class,
        SearchImageEntity::class,
        BookmarkImageEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getCuratedImageDao(): CuratedImageDao
    abstract fun getImageDao(): ImageDao
    abstract fun getSearchImageDao(): SearchImageDao
    abstract fun bookmarkImageDao(): BookmarkImageDao
}