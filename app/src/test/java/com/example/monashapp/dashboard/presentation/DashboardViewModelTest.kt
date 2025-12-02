package com.example.monashapp.dashboard.presentation

import app.cash.turbine.test
import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

private class FakeDashboardRepository : DashboardRepository {
    override fun observeDashboard(): Flow<DashboardData> = flowOf(
        DashboardData(
            greeting = "Hey, Kier",
            dateLabel = "Today, 10 March",
            todaySessions = listOf(
                TodaySession(
                    id = "session_1",
                    startTime = "10.30am",
                    endTime = "1.30pm",
                    title = "FIT2001: Tutorial",
                    subtitle = "S4, 13 College Walk, Clayton",
                    category = SessionCategory.CLASS
                )
            ),
            upcomingLabel = "Sun, 12 March",
            upcomingTasks = listOf(
                UpcomingTask(
                    id = "task_1",
                    dueTime = "5pm",
                    title = "FIT2050",
                    subtitle = "Not submitted",
                    status = TaskStatus.NOT_SUBMITTED
                )
            ),
            parkingAvailability = listOf(
                ParkingAvailability(id = "parking_1", zoneName = "North", bluePermitAvailable = 12, redPermitAvailable = 5)
            )
        )
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(
            getDashboardData = GetDashboardDataUseCase(FakeDashboardRepository())
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits dashboard data`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Hey, Kier", state.greeting)
            assertEquals("Today, 10 March", state.dateLabel)
            assertEquals(1, state.todaySessions.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
