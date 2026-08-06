package com.example.learnapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.learnapp.data.local.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress")
    fun observeAllProgress(): Flow<List<ProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markComplete(progress: ProgressEntity)

    @Query("DELETE FROM user_progress WHERE lessonId = :lessonId")
    suspend fun markIncomplete(lessonId: String)

    @Query("DELETE FROM user_progress")
    suspend fun clearAll()
}
