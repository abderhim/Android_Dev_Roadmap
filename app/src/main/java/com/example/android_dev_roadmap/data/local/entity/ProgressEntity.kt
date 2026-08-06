package com.example.android_dev_roadmap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class ProgressEntity(
    @PrimaryKey val lessonId: String,
)
