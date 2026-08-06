package com.example.learnapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationToProgress() {
        // Find the progress button and click it
        composeTestRule.onNodeWithContentDescription("Progress").performClick()

        // Verify we are on the Progress screen
        composeTestRule.onNodeWithText("My Progress").assertExists()
    }

    @Test
    fun testNavigationToTopicDetail() {
        // Find a topic (assuming "Jetpack Compose" exists)
        composeTestRule.onNodeWithText("Jetpack Compose").performClick()

        // Verify we are on the Topic Detail screen
        composeTestRule.onNodeWithText("Lessons").assertExists()
    }
}
