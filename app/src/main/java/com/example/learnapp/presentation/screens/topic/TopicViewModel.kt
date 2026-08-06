package com.example.learnapp.presentation.screens.topic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TopicDetailUiState(
    val topic: Topic? = null,
    val progress: UserProgress = UserProgress(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
) {
    val filteredLessons: List<com.example.learnapp.domain.model.Lesson>
        get() =
            if (searchQuery.isBlank()) {
                topic?.lessons ?: emptyList()
            } else {
                topic?.lessons?.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                        it.summary.contains(searchQuery, ignoreCase = true)
                } ?: emptyList()
            }
}

@HiltViewModel
class TopicViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getTopicById: GetTopicByIdUseCase,
        private val observeProgress: ObserveProgressUseCase,
    ) : ViewModel() {
        private val topicId: String = checkNotNull(savedStateHandle["topicId"])
        private val _searchQuery = MutableStateFlow("")

        val uiState: StateFlow<TopicDetailUiState> =
            combine(
                flow { emit(getTopicById(topicId)) },
                observeProgress(),
                _searchQuery,
            ) { topic, progress, query ->
                TopicDetailUiState(
                    topic = topic,
                    progress = progress,
                    searchQuery = query,
                    isLoading = false,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TopicDetailUiState(),
            )

        fun onSearchQueryChange(query: String) {
            _searchQuery.value = query
        }
    }
