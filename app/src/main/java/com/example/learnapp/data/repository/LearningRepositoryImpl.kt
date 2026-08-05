package com.example.learnapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.learnapp.data.datasource.LearningDataSource
import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "progress")

class LearningRepositoryImpl(private val context: Context) : LearningRepository {

    private val COMPLETED_LESSONS_KEY = stringSetPreferencesKey("completed_lessons")

    override fun getAllTopics(): List<Topic> = LearningDataSource.topics

    override fun getTopicById(topicId: String): Topic? =
        LearningDataSource.topics.find { it.id == topicId }

    override fun getLessonById(lessonId: String): Lesson? =
        LearningDataSource.topics.flatMap { it.lessons }.find { it.id == lessonId }

    override fun observeProgress(): Flow<UserProgress> =
        context.dataStore.data.map { prefs ->
            UserProgress(completedLessonIds = prefs[COMPLETED_LESSONS_KEY] ?: emptySet())
        }

    override suspend fun markLessonComplete(lessonId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[COMPLETED_LESSONS_KEY] ?: emptySet()
            prefs[COMPLETED_LESSONS_KEY] = current + lessonId
        }
    }

    override suspend fun markLessonIncomplete(lessonId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[COMPLETED_LESSONS_KEY] ?: emptySet()
            prefs[COMPLETED_LESSONS_KEY] = current - lessonId
        }
    }

    override suspend fun clearAllProgress() {
        context.dataStore.edit { prefs ->
            prefs[COMPLETED_LESSONS_KEY] = emptySet()
        }
    }
}

