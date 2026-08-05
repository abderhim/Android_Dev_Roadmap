package com.example.learnapp.presentation.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learnapp.LearnApp
import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.usecase.GetLessonByIdUseCase
import com.example.learnapp.domain.usecase.MarkLessonCompleteUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LessonUiState(
    val lesson: Lesson? = null,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = true,
    val showCodeExample: Boolean = false
)

class LessonViewModel(
    private val lessonId: String,
    private val getLessonById: GetLessonByIdUseCase,
    private val markLessonComplete: MarkLessonCompleteUseCase,
    private val observeProgress: ObserveProgressUseCase
) : ViewModel() {

    private val _showCode = MutableStateFlow(false)

    val uiState: StateFlow<LessonUiState> = combine(
        flow { emit(getLessonById(lessonId)) },
        observeProgress(),
        _showCode
    ) { lesson, progress, showCode ->
        LessonUiState(
            lesson = lesson,
            isCompleted = progress.isLessonCompleted(lessonId),
            isLoading = false,
            showCodeExample = showCode
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LessonUiState()
    )

    fun toggleCodeExample() {
        _showCode.value = !_showCode.value
    }

    fun markComplete() {
        viewModelScope.launch { markLessonComplete(lessonId) }
    }

    companion object {
        fun factory(lessonId: String, app: LearnApp): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LessonViewModel(
                    lessonId = lessonId,
                    getLessonById = app.container.getLessonById,
                    markLessonComplete = app.container.markLessonComplete,
                    observeProgress = app.container.observeProgress
                )
            }
        }
    }
}

