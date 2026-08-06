package com.example.learnapp.presentation.screens.topic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TopicDetailUiState(
    val topic: Topic? = null,
    val progress: UserProgress = UserProgress(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class TopicViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getTopicById: GetTopicByIdUseCase,
        private val observeProgress: ObserveProgressUseCase,
    ) : ViewModel() {
        private val topicId: String = checkNotNull(savedStateHandle["topicId"])

        val uiState: StateFlow<TopicDetailUiState> =
            combine(
                flow { emit(getTopicById(topicId)) },
                observeProgress(),
            ) { topic, progress ->
                TopicDetailUiState(topic = topic, progress = progress, isLoading = false)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TopicDetailUiState(),
            )
    }
