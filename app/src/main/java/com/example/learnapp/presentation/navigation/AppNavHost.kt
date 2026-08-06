package com.example.learnapp.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.learnapp.presentation.screens.home.HomeScreen
import com.example.learnapp.presentation.screens.lesson.LessonScreen
import com.example.learnapp.presentation.screens.progress.ProgressScreen
import com.example.learnapp.presentation.screens.quiz.QuizScreen
import com.example.learnapp.presentation.screens.topic.TopicDetailScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.Home,
            modifier = modifier,
        ) {
            composable<Screen.Home> {
                HomeScreen(
                    onTopicClick = { topicId -> navController.navigate(Screen.TopicDetail(topicId)) },
                    onProgressClick = { navController.navigate(Screen.Progress) },
                    windowSizeClass = windowSizeClass,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                )
            }

            composable<Screen.Progress> {
                ProgressScreen(
                    onBack = navController::navigateUp,
                    onTopicClick = { topicId -> navController.navigate(Screen.TopicDetail(topicId)) },
                )
            }

            composable<Screen.TopicDetail> { backStack ->
                val route = backStack.toRoute<Screen.TopicDetail>()
                TopicDetailScreen(
                    topicId = route.topicId,
                    onLessonClick = { lessonId -> navController.navigate(Screen.Lesson(lessonId)) },
                    onBack = navController::navigateUp,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                )
            }

            composable<Screen.Lesson> { backStack ->
                val route = backStack.toRoute<Screen.Lesson>()
                LessonScreen(
                    lessonId = route.lessonId,
                    onStartQuiz = { navController.navigate(Screen.Quiz(route.lessonId)) },
                    onBack = navController::navigateUp,
                )
            }

            composable<Screen.Quiz> { backStack ->
                val route = backStack.toRoute<Screen.Quiz>()
                QuizScreen(
                    lessonId = route.lessonId,
                    onFinish = { navController.navigateUp() },
                    onBack = navController::navigateUp,
                )
            }
        }
    }
}
