package com.example.learnapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.learnapp.LearnApp
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetAllTopicsUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val topics: List<Topic> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val searchQuery: String = "",
    val isLoading: Boolean = true
) {
    val filteredTopics: List<Topic>
        get() = if (searchQuery.isBlank()) topics
        else topics.filter { it.title.contains(searchQuery, ignoreCase = true) }

    val totalLessons: Int get() = topics.sumOf { it.lessonCount }
    val completedLessons: Int get() = progress.totalCompleted
    val overallProgress: Float
        get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons
}

class HomeViewModel(
    private val getAllTopics: GetAllTopicsUseCase,
    private val observeProgress: ObserveProgressUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isLoading = MutableStateFlow(true)

    val uiState: StateFlow<HomeUiState> = combine(
        _searchQuery,
        observeProgress(),
        _isLoading
    ) { query, progress, loading ->
        val topics = getAllTopics()
        HomeUiState(
            topics = topics,
            progress = progress,
            searchQuery = query,
            isLoading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    init {
        _isLoading.value = false
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    companion object {
        fun factory(app: LearnApp): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    getAllTopics = app.container.getAllTopics,
                    observeProgress = app.container.observeProgress
                )
            }
        }
    }
}

