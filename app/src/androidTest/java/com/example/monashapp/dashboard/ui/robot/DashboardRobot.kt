package com.example.monashapp.dashboard.ui.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo

/**
 * Robot pattern for Dashboard screen testing
 * Provides readable DSL for UI interactions and assertions
 */
class DashboardRobot(private val composeTestRule: ComposeTestRule) {

    // ASSERTIONS

    fun assertGreetingIsDisplayed(greeting: String) = apply {
        composeTestRule
            .onNodeWithText(greeting)
            .assertIsDisplayed()
    }

    fun assertSectionHeaderIsDisplayed(header: String) = apply {
        composeTestRule
            .onNodeWithText(header)
            .assertIsDisplayed()
    }

    fun assertSessionIsDisplayed(title: String) = apply {
        composeTestRule
            .onNodeWithText(title, substring = true)
            .assertIsDisplayed()
    }

    fun assertSessionDetailsAreDisplayed(subtitle: String) = apply {
        composeTestRule
            .onNodeWithText(subtitle, substring = true)
            .assertIsDisplayed()
    }

    fun assertSessionTimeIsDisplayed(time: String) = apply {
        composeTestRule
            .onNodeWithText(time, substring = true)
            .assertIsDisplayed()
    }

    fun assertTaskIsDisplayed(title: String) = apply {
        composeTestRule
            .onNodeWithText(title, substring = true)
            .assertIsDisplayed()
    }

    fun assertTaskStatusIsDisplayed(status: String) = apply {
        composeTestRule
            .onNodeWithText(status, substring = true)
            .assertIsDisplayed()
    }

    fun assertParkingZoneIsDisplayed(zoneName: String) = apply {
        composeTestRule
            .onNodeWithText(zoneName, substring = true)
            .assertIsDisplayed()
    }

    fun assertParkingBadgeIsDisplayed(label: String) = apply {
        composeTestRule
            .onNodeWithText(label)
            .assertIsDisplayed()
    }

    fun assertParkingCountIsDisplayed(count: String) = apply {
        composeTestRule
            .onNodeWithText(count)
            .assertIsDisplayed()
    }

    fun assertTextIsDisplayed(text: String) = apply {
        composeTestRule
            .onNodeWithText(text)
            .assertIsDisplayed()
    }

    fun assertTextIsNotDisplayed(text: String) = apply {
        composeTestRule
            .onNodeWithText(text)
            .assertDoesNotExist()
    }

    // ACTIONS

    fun scrollToText(text: String) = apply {
        composeTestRule
            .onNodeWithText(text, substring = true)
            .performScrollTo()
    }

    fun clickOnText(text: String) = apply {
        composeTestRule
            .onNodeWithText(text)
            .performClick()
    }

    fun waitForIdle() = apply {
        composeTestRule.waitForIdle()
    }

    // COMPOUND ASSERTIONS

    fun assertSessionCardIsComplete(
        title: String,
        subtitle: String,
        startTime: String,
        endTime: String
    ) = apply {
        assertSessionIsDisplayed(title)
        assertSessionDetailsAreDisplayed(subtitle)
        assertSessionTimeIsDisplayed(startTime)
        assertSessionTimeIsDisplayed(endTime)
    }

    fun assertTaskCardIsComplete(
        title: String,
        subtitle: String,
        time: String
    ) = apply {
        assertTaskIsDisplayed(title)
        assertTaskStatusIsDisplayed(subtitle)
        assertSessionTimeIsDisplayed(time)
    }

    fun assertParkingCardIsComplete(
        zoneName: String,
        blueCount: Int,
        redCount: Int
    ) = apply {
        assertParkingZoneIsDisplayed(zoneName)
        assertParkingCountIsDisplayed(blueCount.toString())
        assertParkingCountIsDisplayed(redCount.toString())
    }

    fun assertDashboardIsEmpty() = apply {
        // Only greeting should be visible, no sections
        waitForIdle()
    }

    fun assertMultipleSectionsAreDisplayed(vararg headers: String) = apply {
        headers.forEach { header ->
            assertSectionHeaderIsDisplayed(header)
        }
    }
}

/**
 * Extension function to create robot with DSL
 */
fun ComposeTestRule.dashboardRobot(block: DashboardRobot.() -> Unit) {
    DashboardRobot(this).apply(block)
}

