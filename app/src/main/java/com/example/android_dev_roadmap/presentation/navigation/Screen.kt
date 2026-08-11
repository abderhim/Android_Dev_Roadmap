package com.example.android_dev_roadmap.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Progress : Screen

    @Serializable
    data class TopicDetail(
        val topicId: String,
    ) : Screen

    @Serializable
    data class Lesson(
        val lessonId: String,
    ) : Screen

    @Serializable
    data class Quiz(
        val lessonId: String,
    ) : Screen
}
