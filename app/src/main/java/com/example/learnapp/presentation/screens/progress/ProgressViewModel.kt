package com.example.learnapp.presentation.screens.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learnapp.LearnApp
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.ClearProgressUseCase
import com.example.learnapp.domain.usecase.GetAllTopicsUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProgressUiState(
    val topics: List<Topic> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val isLoading: Boolean = true
) {
    val totalLessons: Int get() = topics.sumOf { it.lessonCount }
    val completedLessons: Int get() = progress.totalCompleted
    val overallProgress: Float get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons
    val completedTopics: Int get() = topics.count { topic ->
        topic.lessons.all { progress.isLessonCompleted(it.id) }
    }
}

class ProgressViewModel(
    private val getAllTopics: GetAllTopicsUseCase,
    private val observeProgress: ObserveProgressUseCase,
    private val clearProgress: ClearProgressUseCase
) : ViewModel() {

    val uiState: StateFlow<ProgressUiState> = combine(
        flow { emit(getAllTopics()) },
        observeProgress()
    ) { topics, progress ->
        ProgressUiState(topics = topics, progress = progress, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProgressUiState()
    )

    fun clearAllProgress() {
        viewModelScope.launch { clearProgress() }
    }

    companion object {
        fun factory(app: LearnApp): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProgressViewModel(
                    getAllTopics = app.container.getAllTopics,
                    observeProgress = app.container.observeProgress,
                    clearProgress = app.container.clearProgress
                )
            }
        }
    }
}

