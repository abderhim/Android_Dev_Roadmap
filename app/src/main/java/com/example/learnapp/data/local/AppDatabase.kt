package com.example.learnapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.learnapp.data.local.dao.ProgressDao
import com.example.learnapp.data.local.entity.ProgressEntity

@Database(entities = [ProgressEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao

    companion object {
        const val DATABASE_NAME = "learn_app_db"
    }
}
