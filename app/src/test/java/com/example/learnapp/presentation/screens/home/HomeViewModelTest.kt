package com.example.learnapp.presentation.screens.home

import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetAllTopicsUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getAllTopicsUseCase = mockk<GetAllTopicsUseCase>()
    private val observeProgressUseCase = mockk<ObserveProgressUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getAllTopicsUseCase() } returns emptyList()
        every { observeProgressUseCase() } returns flowOf(UserProgress())
        viewModel = HomeViewModel(getAllTopicsUseCase, observeProgressUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() =
        runTest {
            val state = viewModel.uiState.value
            assertEquals(emptyList<com.example.learnapp.domain.model.Topic>(), state.topics)
            assertEquals(false, state.isLoading)
            assertEquals("", state.searchQuery)
        }

    @Test
    fun `search query change updates state`() =
        runTest {
            viewModel.onSearchQueryChange("Compose")
            assertEquals("Compose", viewModel.uiState.value.searchQuery)
        }
}
