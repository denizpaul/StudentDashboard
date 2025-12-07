package com.example.monashapp.dashboard.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.monashapp.dashboard.data.fake.FakeDashboardRepositoryForUI
import com.example.monashapp.dashboard.data.mock.MockDashboardData
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase
import com.example.monashapp.dashboard.presentation.DashboardViewModel
import com.example.monashapp.dashboard.ui.robot.dashboardRobot
import com.example.monashapp.ui.theme.MonashTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for Dashboard Screen
 *
 * Tests cover:
 * - Different data scenarios (sessions, tasks, parking)
 * - Empty states
 * - Multiple sections
 * - Component visibility
 * - User interactions
 */
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeRepository: FakeDashboardRepositoryForUI
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        fakeRepository = FakeDashboardRepositoryForUI()
        val useCase = GetDashboardDataUseCase(fakeRepository)
        viewModel = DashboardViewModel(useCase)
    }

    private fun setContent() {
        composeTestRule.setContent {
            MonashTheme {
                DashboardScreen(
                    uiState = viewModel.uiState.value
                )
            }
        }
    }

    // GREETING TESTS

    @Test
    fun dashboardScreen_displaysGreeting() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Kier")
        }
    }

    @Test
    fun dashboardScreen_displaysCorrectGreetingForDifferentUsers() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getSessionsOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Alex")
        }
    }

    // SECTION TESTS

    @Test
    fun dashboardScreen_displaysDateSectionHeader() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertSectionHeaderIsDisplayed("Today, 10 March")
        }
    }

    @Test
    fun dashboardScreen_displaysSectionTypeHeader() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertSectionHeaderIsDisplayed("Available parking spots")
        }
    }

    @Test
    fun dashboardScreen_displaysMultipleSections() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertMultipleSectionsAreDisplayed(
                "Today, 10 March",
                "Sun, 12 March",
                "Available parking spots"
            )
        }
    }

    // SESSION TESTS

    @Test
    fun dashboardScreen_displaysSessionCard() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertSessionIsDisplayed("FIT2001: Tutorial")
            assertSessionDetailsAreDisplayed("S4, 13 College Walk, Clayton")
            assertSessionTimeIsDisplayed("10.30am")
            assertSessionTimeIsDisplayed("1.30pm")
        }
    }

    @Test
    fun dashboardScreen_displaysMultipleSessions() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getSessionsOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertSessionCardIsComplete(
                title = "FIT3155: Lecture",
                subtitle = "S4, Clayton",
                startTime = "9am",
                endTime = "11am"
            )
            assertSessionCardIsComplete(
                title = "FIT2001: Workshop",
                subtitle = "Building 5, Clayton",
                startTime = "2pm",
                endTime = "4pm"
            )
        }
    }

    // TASK TESTS

    @Test
    fun dashboardScreen_displaysTaskCard() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then - Assert on unique title and status, not time which may duplicate
        composeTestRule.dashboardRobot {
            assertTaskIsDisplayed("MTK1000: Weekly quizzes")
            assertTaskStatusIsDisplayed("Submitted")
        }
    }

    @Test
    fun dashboardScreen_displaysSubmittedTaskStatus() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getTasksOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertTaskIsDisplayed("FIT2001: Quiz")
            assertTaskStatusIsDisplayed("Submitted")
        }
    }

    @Test
    fun dashboardScreen_displaysPendingTaskStatus() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getTasksOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertTaskIsDisplayed("FIT3155: Assignment 3")
            assertTaskStatusIsDisplayed("Not submitted")
        }
    }

    @Test
    fun dashboardScreen_displaysMultipleTasks() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getTasksOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertTaskCardIsComplete(
                title = "FIT3155: Assignment 3",
                subtitle = "Not submitted",
                time = "11.59pm"
            )
            assertTaskCardIsComplete(
                title = "FIT2001: Quiz",
                subtitle = "Submitted",
                time = "5pm"
            )
        }
    }

    // PARKING TESTS

    @Test
    fun dashboardScreen_displaysParkingCard() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertParkingZoneIsDisplayed("North (multi-level)")
            assertParkingCountIsDisplayed("12")
            assertParkingCountIsDisplayed("5")
        }
    }

    @Test
    fun dashboardScreen_displaysMultipleParkingZones() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertParkingCardIsComplete(
                zoneName = "North (multi-level)",
                blueCount = 12,
                redCount = 5
            )
            assertParkingCardIsComplete(
                zoneName = "West 1",
                blueCount = 0,
                redCount = 2
            )
        }
    }

    @Test
    fun dashboardScreen_displaysZeroParkingSpots() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getFullParkingDashboard())
        setContent()

        // Then - Assert on unique zone name only
        // Can't assert on "0" as it appears twice (blue and red permits both 0)
        composeTestRule.dashboardRobot {
            assertParkingZoneIsDisplayed("North (multi-level)")
        }
    }

    // EMPTY STATE TESTS

    @Test
    fun dashboardScreen_displaysGreetingWhenEmpty() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getEmptyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Guest")
        }
    }

    @Test
    fun dashboardScreen_doesNotDisplaySectionsWhenEmpty() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getEmptyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertTextIsNotDisplayed("Today")
            assertTextIsNotDisplayed("Available parking")
        }
    }

    // MIXED CONTENT TESTS

    @Test
    fun dashboardScreen_displaysSessionsAndTasksTogether() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertSessionIsDisplayed("FIT2001: Tutorial")
            assertTaskIsDisplayed("MTK1000: Weekly quizzes")
        }
    }

    @Test
    fun dashboardScreen_displaysBusyDayWithMultipleItems() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getBusyDayDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Busy Student")
            assertSectionHeaderIsDisplayed("Today, 20 March")
            assertSessionIsDisplayed("FIT3155: Lecture")
            assertSessionIsDisplayed("FIT2001: Tutorial")
            assertTaskIsDisplayed("MTK1000: Quiz")
            assertSessionIsDisplayed("FIT3077: Workshop")
        }
    }

    // SCROLLING TESTS

    @Test
    fun dashboardScreen_canScrollToBottomContent() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getCompleteDashboard())
        setContent()

        // When
        composeTestRule.dashboardRobot {
            scrollToText("Available parking spots")

            // Then
            assertParkingZoneIsDisplayed("North (multi-level)")
            assertParkingZoneIsDisplayed("West 1")
        }
    }

    @Test
    fun dashboardScreen_canScrollThroughBusyDay() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getBusyDayDashboard())
        setContent()

        // When/Then
        composeTestRule.dashboardRobot {
            scrollToText("FIT3155: Lecture")
            assertSessionIsDisplayed("FIT3155: Lecture")

            scrollToText("FIT3077: Workshop")
            assertSessionIsDisplayed("FIT3077: Workshop")
        }
    }

    // SPECIFIC SCENARIO TESTS

    @Test
    fun dashboardScreen_sessionsOnlyScenario() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getSessionsOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Alex")
            assertSectionHeaderIsDisplayed("Today, 15 March")
            assertSessionIsDisplayed("FIT3155: Lecture")
            assertSessionIsDisplayed("FIT2001: Workshop")
            assertTextIsNotDisplayed("Available parking")
        }
    }

    @Test
    fun dashboardScreen_tasksOnlyScenario() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getTasksOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Sam")
            assertTaskIsDisplayed("FIT3155: Assignment 3")
            assertTaskIsDisplayed("FIT2001: Quiz")
            assertTextIsNotDisplayed("Available parking")
        }
    }

    @Test
    fun dashboardScreen_parkingOnlyScenario() {
        // Given
        fakeRepository.setMockData(MockDashboardData.getParkingOnlyDashboard())
        setContent()

        // Then
        composeTestRule.dashboardRobot {
            assertGreetingIsDisplayed("Hey, Visitor")
            assertSectionHeaderIsDisplayed("Available parking spots")
            assertParkingZoneIsDisplayed("Campus Center")
            assertParkingCountIsDisplayed("45")
            assertParkingCountIsDisplayed("23")
        }
    }
}

