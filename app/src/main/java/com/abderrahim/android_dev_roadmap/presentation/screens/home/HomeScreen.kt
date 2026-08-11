package com.abderrahim.android_dev_roadmap.presentation.screens.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abderrahim.android_dev_roadmap.domain.model.Difficulty
import com.abderrahim.android_dev_roadmap.domain.model.Topic
import com.abderrahim.android_dev_roadmap.domain.model.UserProgress

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    onTopicClick: (String) -> Unit,
    onProgressClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Android Dev",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "Roadmap",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onProgressClick) {
                        Icon(Icons.Default.TrendingUp, contentDescription = "Progress")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
            )
        },
    ) { paddingValues ->
        val columns = if (isExpanded) 2 else 1

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding =
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    bottom = 24.dp,
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // Progress banner
            item(span = {
                androidx.compose.foundation.lazy.grid
                    .GridItemSpan(maxLineSpan)
            }) {
                ProgressBanner(
                    completed = uiState.completedLessons,
                    total = uiState.totalLessons,
                    progress = uiState.overallProgress,
                    onClick = onProgressClick,
                )
            }

            // Search bar
            item(span = {
                androidx.compose.foundation.lazy.grid
                    .GridItemSpan(maxLineSpan)
            }) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search topics...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        ),
                )
            }

            // Section header
            item(span = {
                androidx.compose.foundation.lazy.grid
                    .GridItemSpan(maxLineSpan)
            }) {
                Text(
                    text = if (uiState.searchQuery.isBlank()) "All Topics" else "Results for \"${uiState.searchQuery}\"",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (uiState.filteredTopics.isEmpty()) {
                item(span = {
                    androidx.compose.foundation.lazy.grid
                        .GridItemSpan(maxLineSpan)
                }) {
                    EmptySearchState(uiState.searchQuery)
                }
            }

            // Topic cards
            itemsIndexed(
                items = uiState.filteredTopics,
                key = { _, topic -> topic.id },
            ) { index, topic ->
                with(sharedTransitionScope) {
                    TopicCard(
                        topic = topic,
                        progress = uiState.progress,
                        onClick = { onTopicClick(topic.id) },
                        modifier =
                            Modifier.sharedElement(
                                rememberSharedContentState(key = "topic_card_${topic.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressBanner(
    completed: Int,
    total: Int,
    progress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "progress_animation",
    )

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Your Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$completed / $total lessons completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                )
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                )
            }
        }
    }
}

@Composable
fun TopicCard(
    topic: Topic,
    progress: UserProgress,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topicProgress = progress.topicProgress(topic)
    val animatedProgress by animateFloatAsState(
        targetValue = topicProgress,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "topic_progress",
    )
    val primaryColor = Color(android.graphics.Color.parseColor(topic.colorHex))
    val secondaryColor = Color(android.graphics.Color.parseColor(topic.secondaryColorHex))

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, pressedElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            // Gradient header
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(primaryColor, secondaryColor)),
                        ).padding(20.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = topic.emoji,
                        style = MaterialTheme.typography.displaySmall,
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = topic.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        DifficultyChip(topic.difficulty)
                    }
                }
            }

            // Bottom section
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = topic.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${topic.lessonCount} lessons · ${topic.estimatedMinutes} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    val completedCount = topic.lessons.count { progress.isLessonCompleted(it.id) }
                    if (completedCount > 0) {
                        Text(
                            text = "$completedCount/${topic.lessonCount}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                        )
                    }
                }

                if (topicProgress > 0f) {
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                        color = primaryColor,
                        trackColor = primaryColor.copy(alpha = 0.15f),
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultyChip(difficulty: Difficulty) {
    val (bgColor, textColor) =
        when (difficulty) {
            Difficulty.BEGINNER -> Color(0xFF4CAF50).copy(alpha = 0.2f) to Color(0xFF2E7D32)
            Difficulty.INTERMEDIATE -> Color(0xFFFF9800).copy(alpha = 0.2f) to Color(0xFFE65100)
            Difficulty.ADVANCED -> Color(0xFFF44336).copy(alpha = 0.2f) to Color(0xFFB71C1C)
        }
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(bgColor)
                .padding(horizontal = 8.dp, vertical = 2.dp),
    ) {
        Text(
            text = difficulty.label,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun EmptySearchState(query: String) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🔍", style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "No results for \"$query\"",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
