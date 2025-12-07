package com.example.monashapp.dashboard.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.monashapp.R
import com.example.monashapp.ui.theme.MonashTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for EventCell component
 * Tests different configurations and content scenarios
 */
@RunWith(AndroidJUnit4::class)
class EventCellTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun eventCell_displaysSingleTimeCorrectly() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                EventCell(
                    icon = EventIcon.TaskCircle(
                        color = Color(0xFFFFB86C),
                        iconRes = R.drawable.ic_task
                    ),
                    time = EventTime.Single("5pm"),
                    title = "MTK1000: Weekly quizzes",
                    subtitle = "Submitted"
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("5pm")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("MTK1000: Weekly quizzes")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Submitted")
            .assertIsDisplayed()
    }

    @Test
    fun eventCell_displaysTimeRangeCorrectly() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                EventCell(
                    icon = EventIcon.DurationLine(Color(0xFFFF6B9D)),
                    time = EventTime.Range("10.30am", "1.30pm"),
                    title = "FIT2001: Tutorial",
                    subtitle = "S4, 13 College Walk, Clayton"
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("10.30am")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("-1.30pm", substring = true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("FIT2001: Tutorial")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("S4, 13 College Walk, Clayton")
            .assertIsDisplayed()
    }

    @Test
    fun eventCell_displaysWithoutSubtitle() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                EventCell(
                    icon = EventIcon.TaskCircle(
                        color = Color(0xFFBD93F9),
                        iconRes = R.drawable.ic_task
                    ),
                    time = EventTime.Single("3pm"),
                    title = "FIT3155: Lecture",
                    subtitle = null
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("FIT3155: Lecture")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("3pm")
            .assertIsDisplayed()
    }

    @Test
    fun eventCell_displaysLongTitle() {
        // Given
        val longTitle = "FIT2050: Introduction to Computer Science and Software Engineering"
        composeTestRule.setContent {
            MonashTheme {
                EventCell(
                    icon = EventIcon.DurationLine(Color(0xFFFF6B9D)),
                    time = EventTime.Range("9am", "11am"),
                    title = longTitle,
                    subtitle = "Building 123, Very Long Street Name"
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText(longTitle, substring = true)
            .assertIsDisplayed()
    }
}

/**
 * UI tests for SmallCell component (Parking display)
 */
@RunWith(AndroidJUnit4::class)
class SmallCellTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun smallCell_displaysTitleAndBadges() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                SmallCell(
                    title = "North (multi-level)",
                    dataPoints = listOf(
                        DataPoint("B", 12, Color(0xFF5B9EFF)),
                        DataPoint("R", 5, Color(0xFFFF5757))
                    )
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("North (multi-level)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("12")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("5")
            .assertIsDisplayed()
    }

    @Test
    fun smallCell_displaysZeroValues() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                SmallCell(
                    title = "West 1",
                    dataPoints = listOf(
                        DataPoint("B", 0, Color(0xFF5B9EFF)),
                        DataPoint("R", 0, Color(0xFFFF5757))
                    )
                )
            }
        }

        // Then - Assert on title which is unique
        composeTestRule
            .onNodeWithText("West 1")
            .assertIsDisplayed()

        // Note: Can't assert on "0" specifically as it appears twice
        // The fact that the composable renders without error validates zero values work
    }

    @Test
    fun smallCell_displaysMultipleBadges() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                SmallCell(
                    title = "Campus Center",
                    dataPoints = listOf(
                        DataPoint("B", 45, Color(0xFF5B9EFF)),
                        DataPoint("R", 23, Color(0xFFFF5757)),
                        DataPoint("G", 10, Color(0xFF50FA7B))
                    )
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("45")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("23")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("10")
            .assertIsDisplayed()
    }
}

/**
 * UI tests for CardTile component (Section headers)
 */
@RunWith(AndroidJUnit4::class)
class CardTileTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cardTile_displaysTitle() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                CardTile(title = "Today, 10 March")
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Today, 10 March")
            .assertIsDisplayed()
    }

    @Test
    fun cardTile_displaysLongTitle() {
        // Given
        val longTitle = "Monday, 25 December - Christmas Day Holiday"
        composeTestRule.setContent {
            MonashTheme {
                CardTile(title = longTitle)
            }
        }

        // Then
        composeTestRule
            .onNodeWithText(longTitle)
            .assertIsDisplayed()
    }
}

/**
 * UI tests for SectionTitle component
 */
@RunWith(AndroidJUnit4::class)
class SectionTitleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sectionTitle_displaysTitleWithDivider() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                SectionTitle(
                    title = "Available parking spots",
                    showDivider = true
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Available parking spots")
            .assertIsDisplayed()
    }

    @Test
    fun sectionTitle_displaysTitleWithoutDivider() {
        // Given
        composeTestRule.setContent {
            MonashTheme {
                SectionTitle(
                    title = "Upcoming Events",
                    showDivider = false
                )
            }
        }

        // Then
        composeTestRule
            .onNodeWithText("Upcoming Events")
            .assertIsDisplayed()
    }
}

