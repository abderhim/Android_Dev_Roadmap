package com.example.learnapp.domain.model

data class Topic(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val colorHex: String,
    val secondaryColorHex: String,
    val difficulty: Difficulty,
    val estimatedMinutes: Int,
    val lessons: List<Lesson>,
) {
    val lessonCount: Int get() = lessons.size
}

data class Lesson(
    val id: String,
    val topicId: String,
    val order: Int,
    val title: String,
    val summary: String,
    val content: String,
    val codeExample: String,
    val keyPoints: List<String>,
    val questions: List<Question>,
)

data class Question(
    val id: String,
    val lessonId: String,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)

data class UserProgress(
    val completedLessonIds: Set<String> = emptySet(),
) {
    fun isLessonCompleted(lessonId: String) = lessonId in completedLessonIds

    fun topicProgress(topic: Topic): Float {
        if (topic.lessons.isEmpty()) return 0f
        val completed = topic.lessons.count { isLessonCompleted(it.id) }
        return completed.toFloat() / topic.lessons.size
    }

    val totalCompleted: Int get() = completedLessonIds.size
}

enum class Difficulty(
    val label: String,
) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
}

data class QuizResult(
    val lessonId: String,
    val score: Int,
    val total: Int,
    val passed: Boolean = score.toFloat() / total >= 0.7f,
)
