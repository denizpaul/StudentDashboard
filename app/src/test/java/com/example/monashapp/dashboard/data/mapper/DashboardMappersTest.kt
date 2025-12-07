package com.example.monashapp.dashboard.data.mapper

import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.data.model.LocalDashboardItem
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.LocalDashboardSection
import com.example.monashapp.dashboard.data.model.LocalParkingBadge
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DashboardMappersTest {

    @Test
    fun `LocalDashboardPayload toDomain maps correctly`() {
        // Given
        val localPayload = LocalDashboardPayload(
            greeting = "Hey, Kier",
            sections = listOf(
                LocalDashboardSection(
                    header = "Today, 10 March",
                    headerType = "date",
                    date = "2024-03-10",
                    items = emptyList()
                )
            )
        )

        // When
        val result = localPayload.toDomain()

        // Then
        assertEquals("Hey, Kier", result.greeting)
        assertEquals(1, result.sections.size)
        assertEquals("Today, 10 March", result.sections[0].header)
    }

    @Test
    fun `LocalDashboardSection toDomain maps date headerType correctly`() {
        // Given
        val localSection = LocalDashboardSection(
            header = "Today, 10 March",
            headerType = "date",
            date = "2024-03-10",
            items = emptyList()
        )

        // When
        val result = localSection.toDomain()

        // Then
        assertEquals("Today, 10 March", result.header)
        assertEquals(HeaderType.DATE, result.headerType)
        assertEquals("2024-03-10", result.date)
    }

    @Test
    fun `LocalDashboardSection toDomain maps section headerType correctly`() {
        // Given
        val localSection = LocalDashboardSection(
            header = "Available parking spots",
            headerType = "section",
            items = emptyList()
        )

        // When
        val result = localSection.toDomain()

        // Then
        assertEquals("Available parking spots", result.header)
        assertEquals(HeaderType.SECTION, result.headerType)
    }

    @Test
    fun `LocalDashboardSection toDomain handles unknown headerType`() {
        // Given
        val localSection = LocalDashboardSection(
            header = "Unknown",
            headerType = "unknown",
            items = emptyList()
        )

        // When
        val result = localSection.toDomain()

        // Then
        assertEquals(HeaderType.SECTION, result.headerType) // defaults to SECTION
    }

    @Test
    fun `LocalSession toDomain maps correctly`() {
        // Given
        val localSession = LocalDashboardItem.LocalSession(
            id = "evt_001",
            iconColor = "#FF6B9D",
            startTime = "10.30am",
            endTime = "1.30pm",
            startDateTime = "2024-03-10T10:30:00Z",
            endDateTime = "2024-03-10T13:30:00Z",
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton"
        )

        // When
        val result = localSession.toDomain()

        // Then
        assertIs<DashboardItem.Session>(result)
        assertEquals("evt_001", result.id)
        assertEquals("#FF6B9D", result.iconColor)
        assertEquals("10.30am", result.startTime)
        assertEquals("1.30pm", result.endTime)
        assertEquals("FIT2001: Tutorial", result.title)
        assertEquals("S4, 13 College Walk, Clayton", result.subtitle)
    }

    @Test
    fun `LocalTask toDomain maps correctly with submitted status`() {
        // Given
        val localTask = LocalDashboardItem.LocalTask(
            id = "task_001",
            iconColor = "#FFB86C",
            time = "5pm",
            dateTime = "2024-03-10T17:00:00Z",
            title = "MTK1000: Weekly quizzes",
            subtitle = "Submitted",
            status = "submitted"
        )

        // When
        val result = localTask.toDomain()

        // Then
        assertIs<DashboardItem.Task>(result)
        assertEquals("task_001", result.id)
        assertEquals("#FFB86C", result.iconColor)
        assertEquals("5pm", result.time)
        assertEquals("MTK1000: Weekly quizzes", result.title)
        assertEquals(TaskStatus.SUBMITTED, result.status)
    }

    @Test
    fun `LocalTask toDomain maps correctly with pending status`() {
        // Given
        val localTask = LocalDashboardItem.LocalTask(
            id = "task_002",
            iconColor = "#BD93F9",
            time = "5pm",
            dateTime = "2024-03-12T17:00:00Z",
            title = "FIT2050: Quiz",
            subtitle = "Not submitted",
            status = "pending"
        )

        // When
        val result = localTask.toDomain()

        // Then
        assertIs<DashboardItem.Task>(result)
        assertEquals(TaskStatus.PENDING, result.status)
    }

    @Test
    fun `LocalTask toDomain defaults to pending for unknown status`() {
        // Given
        val localTask = LocalDashboardItem.LocalTask(
            id = "task_003",
            iconColor = "#BD93F9",
            time = "5pm",
            dateTime = "2024-03-12T17:00:00Z",
            title = "Test",
            subtitle = "Test",
            status = "unknown_status"
        )

        // When
        val result = localTask.toDomain()

        // Then
        assertIs<DashboardItem.Task>(result)
        assertEquals(TaskStatus.PENDING, result.status)
    }

    @Test
    fun `LocalParking toDomain maps correctly`() {
        // Given
        val localParking = LocalDashboardItem.LocalParking(
            id = "parking_001",
            title = "North (multi-level)",
            badges = listOf(
                LocalParkingBadge(label = "B", value = 12, color = "#5B9EFF"),
                LocalParkingBadge(label = "R", value = 5, color = "#FF5757")
            ),
            lastUpdated = "2024-03-10T09:45:00Z"
        )

        // When
        val result = localParking.toDomain()

        // Then
        assertIs<DashboardItem.Parking>(result)
        assertEquals("parking_001", result.id)
        assertEquals("North (multi-level)", result.title)
        assertEquals(2, result.badges.size)
        assertEquals("B", result.badges[0].label)
        assertEquals(12, result.badges[0].value)
        assertEquals("#5B9EFF", result.badges[0].color)
    }

    @Test
    fun `LocalParkingBadge toDomain maps correctly`() {
        // Given
        val localBadge = LocalParkingBadge(
            label = "B",
            value = 12,
            color = "#5B9EFF"
        )

        // When
        val result = localBadge.toDomain()

        // Then
        assertEquals("B", result.label)
        assertEquals(12, result.value)
        assertEquals("#5B9EFF", result.color)
    }

    @Test
    fun `complete LocalDashboardPayload with all item types maps correctly`() {
        // Given
        val localPayload = LocalDashboardPayload(
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
                                LocalParkingBadge(label = "B", value = 12, color = "#5B9EFF")
                            ),
                            lastUpdated = "2024-03-10T09:45:00Z"
                        )
                    )
                )
            )
        )

        // When
        val result = localPayload.toDomain()

        // Then
        assertEquals("Hey, Kier", result.greeting)
        assertEquals(2, result.sections.size)

        // Validate first section (date type)
        val dateSection = result.sections[0]
        assertEquals(HeaderType.DATE, dateSection.headerType)
        assertEquals(2, dateSection.items.size)
        assertIs<DashboardItem.Session>(dateSection.items[0])
        assertIs<DashboardItem.Task>(dateSection.items[1])

        // Validate second section (section type)
        val parkingSection = result.sections[1]
        assertEquals(HeaderType.SECTION, parkingSection.headerType)
        assertEquals(1, parkingSection.items.size)
        assertIs<DashboardItem.Parking>(parkingSection.items[0])
    }
}

