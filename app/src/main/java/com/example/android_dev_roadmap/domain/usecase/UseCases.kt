package com.example.android_dev_roadmap.domain.usecase

import com.example.android_dev_roadmap.domain.model.Lesson
import com.example.android_dev_roadmap.domain.model.Topic
import com.example.android_dev_roadmap.domain.model.UserProgress
import com.example.android_dev_roadmap.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow

class GetAllTopicsUseCase(
    private val repository: LearningRepository,
) {
    operator fun invoke(): List<Topic> = repository.getAllTopics()
}

class GetTopicByIdUseCase(
    private val repository: LearningRepository,
) {
    operator fun invoke(topicId: String): Topic? = repository.getTopicById(topicId)
}

class GetLessonByIdUseCase(
    private val repository: LearningRepository,
) {
    operator fun invoke(lessonId: String): Lesson? = repository.getLessonById(lessonId)
}

class ObserveProgressUseCase(
    private val repository: LearningRepository,
) {
    operator fun invoke(): Flow<UserProgress> = repository.observeProgress()
}

class MarkLessonCompleteUseCase(
    private val repository: LearningRepository,
) {
    suspend operator fun invoke(lessonId: String) = repository.markLessonComplete(lessonId)
}

class MarkLessonIncompleteUseCase(
    private val repository: LearningRepository,
) {
    suspend operator fun invoke(lessonId: String) = repository.markLessonIncomplete(lessonId)
}

class ClearProgressUseCase(
    private val repository: LearningRepository,
) {
    suspend operator fun invoke() = repository.clearAllProgress()
}
