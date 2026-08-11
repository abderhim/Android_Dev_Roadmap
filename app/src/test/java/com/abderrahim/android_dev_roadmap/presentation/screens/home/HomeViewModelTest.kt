package com.abderrahim.android_dev_roadmap.presentation.screens.home

import com.abderrahim.android_dev_roadmap.domain.model.UserProgress
import com.abderrahim.android_dev_roadmap.domain.usecase.GetAllTopicsUseCase
import com.abderrahim.android_dev_roadmap.domain.usecase.ObserveProgressUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(emptyList<com.abderrahim.android_dev_roadmap.domain.model.Topic>(), state.topics)
            assertEquals(false, state.isLoading)
            assertEquals("", state.searchQuery)

            collectJob.cancel()
        }

    @Test
    fun `search query change updates state`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

            viewModel.onSearchQueryChange("Compose")
            advanceUntilIdle()

            assertEquals("Compose", viewModel.uiState.value.searchQuery)

            collectJob.cancel()
        }
}
