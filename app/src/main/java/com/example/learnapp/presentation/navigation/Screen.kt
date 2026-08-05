package com.example.learnapp.presentation.navigation

object Screen {
    const val HOME = "home"
    const val PROGRESS = "progress"
    const val TOPIC = "topic/{topicId}"
    const val LESSON = "lesson/{lessonId}"
    const val QUIZ = "quiz/{lessonId}"

    fun topicRoute(topicId: String) = "topic/$topicId"
    fun lessonRoute(lessonId: String) = "lesson/$lessonId"
    fun quizRoute(lessonId: String) = "quiz/$lessonId"
}

