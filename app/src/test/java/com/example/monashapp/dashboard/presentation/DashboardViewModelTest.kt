package com.example.monashapp.dashboard.presentation

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.DashboardSection
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.ParkingBadge
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DashboardViewModel
    private lateinit var fakeUseCase: FakeGetDashboardDataUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeUseCase = FakeGetDashboardDataUseCase()
        viewModel = DashboardViewModel(fakeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel loads dashboard data on initialization`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals("Hey, Kier", state.greeting)
        assertEquals(2, state.sections.size)
        assertFalse(state.isLoading)
    }

    @Test
    fun `uiState contains correct sections`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals("Today, 10 March", state.sections[0].header)
        assertEquals(HeaderType.DATE, state.sections[0].headerType)
        assertEquals("2024-03-10", state.sections[0].date)

        assertEquals("Available parking spots", state.sections[1].header)
        assertEquals(HeaderType.SECTION, state.sections[1].headerType)
    }

    @Test
    fun `uiState contains session items`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        val sessionItem = state.sections[0].items[0]

        assertIs<DashboardItem.Session>(sessionItem)
        assertEquals("FIT2001: Tutorial", sessionItem.title)
        assertEquals("10.30am", sessionItem.startTime)
        assertEquals("1.30pm", sessionItem.endTime)
    }

    @Test
    fun `uiState contains task items`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        val taskItem = state.sections[0].items[1]

        assertIs<DashboardItem.Task>(taskItem)
        assertEquals("MTK1000: Weekly quizzes", taskItem.title)
        assertEquals(TaskStatus.SUBMITTED, taskItem.status)
    }

    @Test
    fun `uiState contains parking items`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        val parkingItem = state.sections[1].items[0]

        assertIs<DashboardItem.Parking>(parkingItem)
        assertEquals("North (multi-level)", parkingItem.title)
        assertEquals(2, parkingItem.badges.size)
    }

    @Test
    fun `onRefresh event triggers data reload`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        fakeUseCase.updateData(
            DashboardData(
                greeting = "Hey, Refreshed",
                sections = emptyList()
            )
        )

        viewModel.onEvent(DashboardUiEvent.OnRefresh)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Hey, Refreshed", state.greeting)
    }

    @Test
    fun `multiple sections are handled correctly`() = runTest {
        fakeUseCase.updateData(
            DashboardData(
                greeting = "Hey, Multi",
                sections = listOf(
                    DashboardSection("Section 1", HeaderType.DATE, items = emptyList()),
                    DashboardSection("Section 2", HeaderType.DATE, items = emptyList()),
                    DashboardSection("Section 3", HeaderType.SECTION, items = emptyList())
                )
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(3, state.sections.size)
        assertEquals("Section 1", state.sections[0].header)
        assertEquals("Section 2", state.sections[1].header)
        assertEquals("Section 3", state.sections[2].header)
    }

    @Test
    fun `empty sections are handled correctly`() = runTest {
        fakeUseCase.updateData(
            DashboardData(
                greeting = "Hey, Empty",
                sections = emptyList()
            )
        )

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Hey, Empty", state.greeting)
        assertEquals(0, state.sections.size)
    }
}

private class FakeGetDashboardDataUseCase : GetDashboardDataUseCase(FakeDashboardRepository()) {
    private val dataFlow = MutableStateFlow(createDefaultData())

    fun updateData(newData: DashboardData) {
        dataFlow.value = newData
    }

    override fun invoke(): Flow<DashboardData> = dataFlow

    private fun createDefaultData() = DashboardData(
        greeting = "Hey, Kier",
        sections = listOf(
            DashboardSection(
                header = "Today, 10 March",
                headerType = HeaderType.DATE,
                date = "2024-03-10",
                items = listOf(
                    DashboardItem.Session(
                        id = "evt_001",
                        iconColor = "#FF6B9D",
                        startTime = "10.30am",
                        endTime = "1.30pm",
                        startDateTime = "2024-03-10T10:30:00Z",
                        endDateTime = "2024-03-10T13:30:00Z",
                        title = "FIT2001: Tutorial",
                        subtitle = "S4, 13 College Walk, Clayton"
                    ),
                    DashboardItem.Task(
                        id = "task_001",
                        iconColor = "#FFB86C",
                        time = "5pm",
                        dateTime = "2024-03-10T17:00:00Z",
                        title = "MTK1000: Weekly quizzes",
                        subtitle = "Submitted",
                        status = TaskStatus.SUBMITTED
                    )
                )
            ),
            DashboardSection(
                header = "Available parking spots",
                headerType = HeaderType.SECTION,
                items = listOf(
                    DashboardItem.Parking(
                        id = "parking_001",
                        title = "North (multi-level)",
                        badges = listOf(
                            ParkingBadge(label = "B", value = 12, color = "#5B9EFF"),
                            ParkingBadge(label = "R", value = 5, color = "#FF5757")
                        ),
                        lastUpdated = "2024-03-10T09:45:00Z"
                    )
                )
            )
        )
    )
}

private class FakeDashboardRepository : DashboardRepository {
    override fun observeDashboard(): Flow<DashboardData> = flowOf()
}

