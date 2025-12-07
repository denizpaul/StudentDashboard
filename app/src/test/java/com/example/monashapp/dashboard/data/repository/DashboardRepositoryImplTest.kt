package com.example.monashapp.dashboard.data.repository

import app.cash.turbine.test
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.data.local.DashboardLocalDataSource
import com.example.monashapp.dashboard.data.model.LocalDashboardItem
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.LocalDashboardSection
import com.example.monashapp.dashboard.data.model.LocalParkingBadge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DashboardRepositoryImplTest {

    private lateinit var repository: DashboardRepositoryImpl
    private lateinit var fakeDataSource: FakeDashboardLocalDataSource

    @Before
    fun setup() {
        fakeDataSource = FakeDashboardLocalDataSource()
        repository = DashboardRepositoryImpl(fakeDataSource)
    }

    @Test
    fun `observeDashboard returns mapped domain data`() = runTest {
        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()

            assertEquals("Hey, Kier", emission.greeting)
            assertEquals(2, emission.sections.size)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard maps sections correctly`() = runTest {
        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()
            val sections = emission.sections

            // Validate first section
            assertEquals("Today, 10 March", sections[0].header)
            assertEquals(HeaderType.DATE, sections[0].headerType)
            assertEquals("2024-03-10", sections[0].date)

            // Validate second section
            assertEquals("Available parking spots", sections[1].header)
            assertEquals(HeaderType.SECTION, sections[1].headerType)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard maps session items correctly`() = runTest {
        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()
            val sessionItem = emission.sections[0].items[0]

            assertIs<DashboardItem.Session>(sessionItem)
            assertEquals("evt_001", sessionItem.id)
            assertEquals("#FF6B9D", sessionItem.iconColor)
            assertEquals("10.30am", sessionItem.startTime)
            assertEquals("1.30pm", sessionItem.endTime)
            assertEquals("FIT2001: Tutorial", sessionItem.title)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard maps task items correctly`() = runTest {
        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()
            val taskItem = emission.sections[0].items[1]

            assertIs<DashboardItem.Task>(taskItem)
            assertEquals("task_001", taskItem.id)
            assertEquals("#FFB86C", taskItem.iconColor)
            assertEquals("5pm", taskItem.time)
            assertEquals("MTK1000: Weekly quizzes", taskItem.title)
            assertEquals(TaskStatus.SUBMITTED, taskItem.status)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard maps parking items correctly`() = runTest {
        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()
            val parkingItem = emission.sections[1].items[0]

            assertIs<DashboardItem.Parking>(parkingItem)
            assertEquals("parking_001", parkingItem.id)
            assertEquals("North (multi-level)", parkingItem.title)
            assertEquals(2, parkingItem.badges.size)
            assertEquals("B", parkingItem.badges[0].label)
            assertEquals(12, parkingItem.badges[0].value)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard handles empty sections`() = runTest {
        // Given
        fakeDataSource.setData(
            LocalDashboardPayload(
                greeting = "Hey, Test",
                sections = emptyList()
            )
        )

        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()

            assertEquals("Hey, Test", emission.greeting)
            assertEquals(0, emission.sections.size)

            awaitComplete()
        }
    }

    @Test
    fun `observeDashboard handles section with no items`() = runTest {
        // Given
        fakeDataSource.setData(
            LocalDashboardPayload(
                greeting = "Hey, Test",
                sections = listOf(
                    LocalDashboardSection(
                        header = "Empty Section",
                        headerType = "date",
                        items = emptyList()
                    )
                )
            )
        )

        // When
        repository.observeDashboard().test {
            // Then
            val emission = awaitItem()

            assertEquals(1, emission.sections.size)
            assertEquals(0, emission.sections[0].items.size)

            awaitComplete()
        }
    }
}

private class FakeDashboardLocalDataSource : DashboardLocalDataSource {
    private var data: LocalDashboardPayload = createDefaultPayload()

    fun setData(newData: LocalDashboardPayload) {
        data = newData
    }

    override fun observeDashboard(): Flow<LocalDashboardPayload> = flowOf(data)

    private fun createDefaultPayload() = LocalDashboardPayload(
        greeting = "Hey, Kier",
        sections = listOf(
            LocalDashboardSection(
                header = "Today, 10 March",
                headerType = "date",
                date = "2024-03-10",
                items = listOf(
                    LocalDashboardItem.LocalSession(
                        id = "evt_001",
                        iconColor = "#FF6B9D",
                        startTime = "10.30am",
                        endTime = "1.30pm",
                        startDateTime = "2024-03-10T10:30:00Z",
                        endDateTime = "2024-03-10T13:30:00Z",
                        title = "FIT2001: Tutorial",
                        subtitle = "S4, 13 College Walk, Clayton"
                    ),
                    LocalDashboardItem.LocalTask(
                        id = "task_001",
                        iconColor = "#FFB86C",
                        time = "5pm",
                        dateTime = "2024-03-10T17:00:00Z",
                        title = "MTK1000: Weekly quizzes",
                        subtitle = "Submitted",
                        status = "submitted"
                    )
                )
            ),
            LocalDashboardSection(
                header = "Available parking spots",
                headerType = "section",
                items = listOf(
                    LocalDashboardItem.LocalParking(
                        id = "parking_001",
                        title = "North (multi-level)",
                        badges = listOf(
                            LocalParkingBadge(label = "B", value = 12, color = "#5B9EFF"),
                            LocalParkingBadge(label = "R", value = 5, color = "#FF5757")
                        ),
                        lastUpdated = "2024-03-10T09:45:00Z"
                    )
                )
            )
        )
    )
}

