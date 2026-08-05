package com.example.learnapp

import android.app.Application
import android.content.Context
import com.example.learnapp.data.repository.LearningRepositoryImpl
import com.example.learnapp.domain.repository.LearningRepository
import com.example.learnapp.domain.usecase.ClearProgressUseCase
import com.example.learnapp.domain.usecase.GetAllTopicsUseCase
import com.example.learnapp.domain.usecase.GetLessonByIdUseCase
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.MarkLessonCompleteUseCase
import com.example.learnapp.domain.usecase.MarkLessonIncompleteUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase

class AppContainer(context: Context) {
    private val repository: LearningRepository = LearningRepositoryImpl(context)

    val getAllTopics = GetAllTopicsUseCase(repository)
    val getTopicById = GetTopicByIdUseCase(repository)
    val getLessonById = GetLessonByIdUseCase(repository)
    val observeProgress = ObserveProgressUseCase(repository)
    val markLessonComplete = MarkLessonCompleteUseCase(repository)
    val markLessonIncomplete = MarkLessonIncompleteUseCase(repository)
    val clearProgress = ClearProgressUseCase(repository)
}

class LearnApp : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}

