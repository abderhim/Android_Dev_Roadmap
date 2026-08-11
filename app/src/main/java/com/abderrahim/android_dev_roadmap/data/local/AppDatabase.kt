package com.abderrahim.android_dev_roadmap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abderrahim.android_dev_roadmap.data.local.dao.ProgressDao
import com.abderrahim.android_dev_roadmap.data.local.entity.ProgressEntity

@Database(entities = [ProgressEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao

    companion object {
        const val DATABASE_NAME = "learn_app_db"
    }
}
