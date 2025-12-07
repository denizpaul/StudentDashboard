package com.example.monashapp.dashboard.data.local

import com.example.monashapp.dashboard.data.model.LocalDashboardItem
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.LocalDashboardSection
import com.example.monashapp.dashboard.data.model.LocalParkingBadge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeDashboardLocalDataSource : DashboardLocalDataSource {
    private val payload = MutableStateFlow(createPayload())

    override fun observeDashboard(): Flow<LocalDashboardPayload> = payload.asStateFlow()

    private fun createPayload(): LocalDashboardPayload = LocalDashboardPayload(
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
                header = "Sun, 12 March",
                headerType = "date",
                date = "2024-03-12",
                items = listOf(
                    LocalDashboardItem.LocalTask(
                        id = "task_002",
                        iconColor = "#BD93F9",
                        time = "5pm",
                        dateTime = "2024-03-12T17:00:00Z",
                        title = "FIT2050: In-class quizzes submission closes",
                        subtitle = "Not submitted",
                        status = "pending"
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
                    ),
                    LocalDashboardItem.LocalParking(
                        id = "parking_002",
                        title = "West 1",
                        badges = listOf(
                            LocalParkingBadge(label = "B", value = 0, color = "#5B9EFF"),
                            LocalParkingBadge(label = "R", value = 2, color = "#FF5757")
                        ),
                        lastUpdated = "2024-03-10T09:45:00Z"
                    )
                )
            )
        )
    )
}