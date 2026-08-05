package com.example.learnapp.domain.repository

import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
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

