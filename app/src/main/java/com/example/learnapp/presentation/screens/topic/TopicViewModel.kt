package com.example.learnapp.presentation.screens.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learnapp.LearnApp
import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

data class TopicDetailUiState(
    val topic: Topic? = null,
    val progress: UserProgress = UserProgress(),
    val isLoading: Boolean = true
)

class TopicViewModel(
    private val topicId: String,
    private val getTopicById: GetTopicByIdUseCase,
    private val observeProgress: ObserveProgressUseCase
) : ViewModel() {

    val uiState: StateFlow<TopicDetailUiState> = combine(
        flow { emit(getTopicById(topicId)) },
        observeProgress()
    ) { topic, progress ->
        TopicDetailUiState(topic = topic, progress = progress, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TopicDetailUiState()
    )

    companion object {
        fun factory(topicId: String, app: LearnApp): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TopicViewModel(
                    topicId = topicId,
                    getTopicById = app.container.getTopicById,
                    observeProgress = app.container.observeProgress
                )
            }
        }
    }
}

