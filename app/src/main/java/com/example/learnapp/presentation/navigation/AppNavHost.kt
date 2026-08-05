package com.example.learnapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.learnapp.LearnApp
import com.example.learnapp.presentation.screens.home.HomeScreen
import com.example.learnapp.presentation.screens.lesson.LessonScreen
import com.example.learnapp.presentation.screens.progress.ProgressScreen
import com.example.learnapp.presentation.screens.quiz.QuizScreen
import com.example.learnapp.presentation.screens.topic.TopicDetailScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    app: LearnApp,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HOME,
        modifier = modifier
    ) {
        composable(Screen.HOME) {
            HomeScreen(
                app = app,
                onTopicClick = { topicId -> navController.navigate(Screen.topicRoute(topicId)) },
                onProgressClick = { navController.navigate(Screen.PROGRESS) }
            )
        }

        composable(Screen.PROGRESS) {
            ProgressScreen(
                app = app,
                onBack = navController::navigateUp,
                onTopicClick = { topicId -> navController.navigate(Screen.topicRoute(topicId)) }
            )
        }

        composable(
            route = Screen.TOPIC,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStack ->
            val topicId = backStack.arguments!!.getString("topicId")!!
            TopicDetailScreen(
                topicId = topicId,
                app = app,
                onLessonClick = { lessonId -> navController.navigate(Screen.lessonRoute(lessonId)) },
                onBack = navController::navigateUp
            )
        }

        composable(
            route = Screen.LESSON,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStack ->
            val lessonId = backStack.arguments!!.getString("lessonId")!!
            LessonScreen(
                lessonId = lessonId,
                app = app,
                onStartQuiz = { navController.navigate(Screen.quizRoute(lessonId)) },
                onBack = navController::navigateUp
            )
        }

        composable(
            route = Screen.QUIZ,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStack ->
            val lessonId = backStack.arguments!!.getString("lessonId")!!
            QuizScreen(
                lessonId = lessonId,
                app = app,
                onFinish = { navController.navigateUp() },
                onBack = navController::navigateUp
            )
        }
    }
}

