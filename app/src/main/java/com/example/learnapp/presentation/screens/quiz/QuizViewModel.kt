package com.example.learnapp.presentation.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learnapp.LearnApp
import com.example.learnapp.domain.model.Question
import com.example.learnapp.domain.usecase.GetLessonByIdUseCase
import com.example.learnapp.domain.usecase.MarkLessonCompleteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedAnswer: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val correctCount: Int = 0,
    val isFinished: Boolean = false,
    val lessonTitle: String = ""
) {
    val currentQuestion: Question? get() = questions.getOrNull(currentIndex)
    val isLastQuestion: Boolean get() = currentIndex >= questions.size - 1
    val progress: Float get() = if (questions.isEmpty()) 0f else (currentIndex + 1).toFloat() / questions.size
    val scorePercent: Float get() = if (questions.isEmpty()) 0f else correctCount.toFloat() / questions.size
    val passed: Boolean get() = scorePercent >= 0.7f
}

class QuizViewModel(
    private val lessonId: String,
    private val getLessonById: GetLessonByIdUseCase,
    private val markLessonComplete: MarkLessonCompleteUseCase
) : ViewModel() {

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
                        lessonTitle = lesson.title
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
                    correctCount = newCorrectCount
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
                isFinished = false
            )
        }
    }

    companion object {
        fun factory(lessonId: String, app: LearnApp): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                QuizViewModel(
                    lessonId = lessonId,
                    getLessonById = app.container.getLessonById,
                    markLessonComplete = app.container.markLessonComplete
                )
            }
        }
    }
}

