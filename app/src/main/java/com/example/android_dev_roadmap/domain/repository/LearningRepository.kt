package com.example.android_dev_roadmap.domain.repository

import com.example.android_dev_roadmap.domain.model.Lesson
import com.example.android_dev_roadmap.domain.model.Topic
import com.example.android_dev_roadmap.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface LearningRepository {
    fun getAllTopics(): List<Topic>

    fun getTopicById(topicId: String): Topic?

    fun getLessonById(lessonId: String): Lesson?

    fun observeProgress(): Flow<UserProgress>

    suspend fun markLessonComplete(lessonId: String)

    suspend fun markLessonIncomplete(lessonId: String)

    suspend fun clearAllProgress()
}
