package com.abderrahim.android_dev_roadmap.data.repository

import com.abderrahim.android_dev_roadmap.data.datasource.LearningDataSource
import com.abderrahim.android_dev_roadmap.data.local.dao.ProgressDao
import com.abderrahim.android_dev_roadmap.data.local.entity.ProgressEntity
import com.abderrahim.android_dev_roadmap.domain.model.Lesson
import com.abderrahim.android_dev_roadmap.domain.model.Topic
import com.abderrahim.android_dev_roadmap.domain.model.UserProgress
import com.abderrahim.android_dev_roadmap.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LearningRepositoryImpl(
    private val progressDao: ProgressDao,
) : LearningRepository {
    override fun getAllTopics(): List<Topic> = LearningDataSource.topics

    override fun getTopicById(topicId: String): Topic? = LearningDataSource.topics.find { it.id == topicId }

    override fun getLessonById(lessonId: String): Lesson? = LearningDataSource.topics.flatMap { it.lessons }.find { it.id == lessonId }

    override fun observeProgress(): Flow<UserProgress> =
        progressDao.observeAllProgress().map { entities ->
            UserProgress(completedLessonIds = entities.map { it.lessonId }.toSet())
        }

    override suspend fun markLessonComplete(lessonId: String) {
        progressDao.markComplete(ProgressEntity(lessonId))
    }

    override suspend fun markLessonIncomplete(lessonId: String) {
        progressDao.markIncomplete(lessonId)
    }

    override suspend fun clearAllProgress() {
        progressDao.clearAll()
    }
}
