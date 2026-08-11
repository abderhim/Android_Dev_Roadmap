package com.abderrahim.android_dev_roadmap.presentation.screens.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abderrahim.android_dev_roadmap.domain.model.Question
import com.abderrahim.android_dev_roadmap.domain.usecase.GetLessonByIdUseCase
import com.abderrahim.android_dev_roadmap.domain.usecase.MarkLessonCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val correctCount: Int = 0,
    val isFinished: Boolean = false,
    val lessonTitle: String = "",
) {
    val currentQuestion: Question? get() = questions.getOrNull(currentIndex)
    val isLastQuestion: Boolean get() = currentIndex >= questions.size - 1
    val progress: Float get() = if (questions.isEmpty()) 0f else (currentIndex + 1).toFloat() / questions.size
    val scorePercent: Float get() = if (questions.isEmpty()) 0f else correctCount.toFloat() / questions.size
    val passed: Boolean get() = scorePercent >= 0.7f
}

@HiltViewModel
class QuizViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getLessonById: GetLessonByIdUseCase,
        private val markLessonComplete: MarkLessonCompleteUseCase,
    ) : ViewModel() {
        private val lessonId: String = checkNotNull(savedStateHandle["lessonId"])

        private val _uiState = MutableStateFlow(QuizUiState())
        val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

        init {
            loadQuiz()
        }

        private fun loadQuiz() {
            viewModelScope.launch {
                val lesson = getLessonById(lessonId)
                if (lesson != null) {
                    _uiState.update {
                        it.copy(
                            questions = lesson.questions.shuffled(),
                            lessonTitle = lesson.title,
                        )
                    }
                }
            }
        }

        fun selectAnswer(index: Int) {
            if (_uiState.value.isAnswerRevealed) return
            _uiState.update { it.copy(selectedAnswer = index, isAnswerRevealed = true) }
        }

        fun nextQuestion() {
            val state = _uiState.value
            val isCorrect = state.selectedAnswer == state.currentQuestion?.correctIndex
            val newCorrectCount = state.correctCount + if (isCorrect) 1 else 0

            if (state.isLastQuestion) {
                _uiState.update { it.copy(correctCount = newCorrectCount, isFinished = true) }
                if (state.passed || newCorrectCount.toFloat() / state.questions.size >= 0.7f) {
                    viewModelScope.launch { markLessonComplete(lessonId) }
                }
            } else {
                _uiState.update {
                    it.copy(
                        currentIndex = it.currentIndex + 1,
                        selectedAnswer = null,
                        isAnswerRevealed = false,
                        correctCount = newCorrectCount,
                    )
                }
            }
        }

        fun restartQuiz() {
            _uiState.update {
                it.copy(
                    questions = it.questions.shuffled(),
                    currentIndex = 0,
                    selectedAnswer = null,
                    isAnswerRevealed = false,
                    correctCount = 0,
                    isFinished = false,
                )
            }
        }
    }
