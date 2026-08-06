package com.example.learnapp.presentation.screens.topic

import androidx.lifecycle.SavedStateHandle
import com.example.learnapp.domain.model.Difficulty
import com.example.learnapp.domain.model.Lesson
import com.example.learnapp.domain.model.Topic
import com.example.learnapp.domain.model.UserProgress
import com.example.learnapp.domain.usecase.GetTopicByIdUseCase
import com.example.learnapp.domain.usecase.ObserveProgressUseCase
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
class TopicViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getTopicByIdUseCase = mockk<GetTopicByIdUseCase>()
    private val observeProgressUseCase = mockk<ObserveProgressUseCase>()
    private lateinit var viewModel: TopicViewModel

    private val testTopic =
        Topic(
            id = "test_topic",
            title = "Test Topic",
            description = "Description",
            emoji = "🧪",
            colorHex = "#000000",
            secondaryColorHex = "#FFFFFF",
            difficulty = Difficulty.BEGINNER,
            estimatedMinutes = 10,
            lessons =
                listOf(
                    Lesson(
                        id = "lesson_1",
                        topicId = "test_topic",
                        order = 1,
                        title = "Introduction to CI",
                        summary = "Summary 1",
                        content = "Content 1",
                        codeExample = "",
                        keyPoints = emptyList(),
                        questions = emptyList(),
                    ),
                    Lesson(
                        id = "lesson_2",
                        topicId = "test_topic",
                        order = 2,
                        title = "Advanced CD",
                        summary = "Summary 2",
                        content = "Content 2",
                        codeExample = "",
                        keyPoints = emptyList(),
                        questions = emptyList(),
                    ),
                ),
        )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val savedStateHandle = SavedStateHandle(mapOf("topicId" to "test_topic"))
        every { getTopicByIdUseCase("test_topic") } returns testTopic
        every { observeProgressUseCase() } returns flowOf(UserProgress())
        viewModel = TopicViewModel(savedStateHandle, getTopicByIdUseCase, observeProgressUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searching for lesson filters correctly`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

            viewModel.onSearchQueryChange("CI")
            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(1, state.filteredLessons.size)
            assertEquals("Introduction to CI", state.filteredLessons.first().title)

            collectJob.cancel()
        }

    @Test
    fun `empty search query returns all lessons`() =
        runTest {
            val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

            viewModel.onSearchQueryChange("")
            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertEquals(2, state.filteredLessons.size)

            collectJob.cancel()
        }
}
