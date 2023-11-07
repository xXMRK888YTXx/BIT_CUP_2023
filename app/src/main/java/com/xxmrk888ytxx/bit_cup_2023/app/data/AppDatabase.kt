package com.xxmrk888ytxx.bit_cup_2023.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryDao
import com.xxmrk888ytxx.bit_cup_2023.home.data.database.CategoryEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
}