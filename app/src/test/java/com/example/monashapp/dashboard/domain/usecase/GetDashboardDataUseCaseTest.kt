package com.example.monashapp.dashboard.domain.usecase

import app.cash.turbine.test
import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.DashboardSection
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.ParkingBadge
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetDashboardDataUseCaseTest {

    private lateinit var useCase: GetDashboardDataUseCase
    private lateinit var fakeRepository: FakeDashboardRepository

    @Before
    fun setup() {
        fakeRepository = FakeDashboardRepository()
        useCase = GetDashboardDataUseCase(fakeRepository)
    }

    @Test
    fun `invoke returns dashboard data from repository`() = runTest {
        // When
        useCase().test {
            // Then
            val emission = awaitItem()

            assertEquals("Hey, Kier", emission.greeting)
            assertEquals(2, emission.sections.size)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns sections with correct header types`() = runTest {
        // When
        useCase().test {
            // Then
            val emission = awaitItem()

            assertEquals(HeaderType.DATE, emission.sections[0].headerType)
            assertEquals(HeaderType.SECTION, emission.sections[1].headerType)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns session items correctly`() = runTest {
        // When
        useCase().test {
            // Then
            val emission = awaitItem()
            val sessionItem = emission.sections[0].items[0]

            assertIs<DashboardItem.Session>(sessionItem)
            assertEquals("FIT2001: Tutorial", sessionItem.title)
            assertEquals("10.30am", sessionItem.startTime)
            assertEquals("1.30pm", sessionItem.endTime)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns task items correctly`() = runTest {
        // When
        useCase().test {
            // Then
            val emission = awaitItem()
            val taskItem = emission.sections[0].items[1]

            assertIs<DashboardItem.Task>(taskItem)
            assertEquals("MTK1000: Weekly quizzes", taskItem.title)
            assertEquals("5pm", taskItem.time)
            assertEquals(TaskStatus.SUBMITTED, taskItem.status)

            awaitComplete()
        }
    }

    @Test
    fun `invoke returns parking items correctly`() = runTest {
        // When
        useCase().test {
            // Then
            val emission = awaitItem()
            val parkingItem = emission.sections[1].items[0]

            assertIs<DashboardItem.Parking>(parkingItem)
            assertEquals("North (multi-level)", parkingItem.title)
            assertEquals(2, parkingItem.badges.size)

            awaitComplete()
        }
    }

    @Test
    fun `invoke handles empty data correctly`() = runTest {
        // Given
        fakeRepository.setData(
            DashboardData(
                greeting = "Hey, Empty",
                sections = emptyList()
            )
        )

        // When
        useCase().test {
            // Then
            val emission = awaitItem()

            assertEquals("Hey, Empty", emission.greeting)
            assertEquals(0, emission.sections.size)

            awaitComplete()
        }
    }

    @Test
    fun `invoke handles multiple sections correctly`() = runTest {
        // Given
        fakeRepository.setData(
            DashboardData(
                greeting = "Hey, Multi",
                sections = listOf(
                    DashboardSection(
                        header = "Section 1",
                        headerType = HeaderType.DATE,
                        items = emptyList()
                    ),
                    DashboardSection(
                        header = "Section 2",
                        headerType = HeaderType.DATE,
                        items = emptyList()
                    ),
                    DashboardSection(
                        header = "Section 3",
                        headerType = HeaderType.SECTION,
                        items = emptyList()
                    )
                )
            )
        )

        // When
        useCase().test {
            // Then
            val emission = awaitItem()

            assertEquals(3, emission.sections.size)
            assertEquals("Section 1", emission.sections[0].header)
            assertEquals("Section 2", emission.sections[1].header)
            assertEquals("Section 3", emission.sections[2].header)

            awaitComplete()
        }
    }

    @Test
    fun `invoke propagates data changes from repository`() = runTest {
        // Given - initial data already set in setup

        // When - change data
        fakeRepository.setData(
            DashboardData(
                greeting = "Hey, Updated",
                sections = emptyList()
            )
        )

        // Then
        useCase().test {
            val emission = awaitItem()
            assertEquals("Hey, Updated", emission.greeting)
            awaitComplete()
        }
    }
}

private class FakeDashboardRepository : DashboardRepository {
    private var data: DashboardData = createDefaultData()

    fun setData(newData: DashboardData) {
        data = newData
    }

    override fun observeDashboard(): Flow<DashboardData> = flowOf(data)

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

