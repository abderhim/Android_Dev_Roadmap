package com.example.learnapp.presentation.screens.lesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.usecase.GetLessonByIdUseCase
import com.example.learnapp.domain.usecase.MarkLessonCompleteUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LessonUiState(
    val lesson: Lesson? = null,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = true,
    val showCodeExample: Boolean = false,
)

@HiltViewModel
class LessonViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getLessonById: GetLessonByIdUseCase,
        private val markLessonComplete: MarkLessonCompleteUseCase,
        private val observeProgress: ObserveProgressUseCase,
    ) : ViewModel() {
        private val lessonId: String = checkNotNull(savedStateHandle["lessonId"])

        private val _showCode = MutableStateFlow(false)

        val uiState: StateFlow<LessonUiState> =
            combine(
                flow { emit(getLessonById(lessonId)) },
                observeProgress(),
                _showCode,
            ) { lesson, progress, showCode ->
                LessonUiState(
                    lesson = lesson,
                    isCompleted = progress.isLessonCompleted(lessonId),
                    isLoading = false,
                    showCodeExample = showCode,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LessonUiState(),
            )

        fun toggleCodeExample() {
            _showCode.value = !_showCode.value
        }

        fun markComplete() {
            viewModelScope.launch { markLessonComplete(lessonId) }
        }
    }
