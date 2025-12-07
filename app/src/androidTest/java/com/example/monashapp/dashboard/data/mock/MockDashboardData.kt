package com.example.monashapp.dashboard.data.mock

import com.example.monashapp.dashboard.data.model.LocalDashboardItem
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.LocalDashboardSection
import com.example.monashapp.dashboard.data.model.LocalParkingBadge

/**
 * Provides mock dashboard data for UI testing
 */
object MockDashboardData {

    /**
     * Complete dashboard with all item types
     */
    fun getCompleteDashboard(): LocalDashboardPayload = LocalDashboardPayload(
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

    /**
     * Dashboard with only sessions
     */
    fun getSessionsOnlyDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Alex",
        sections = listOf(
            LocalDashboardSection(
                header = "Today, 15 March",
                headerType = "date",
                date = "2024-03-15",
                items = listOf(
                    LocalDashboardItem.LocalSession(
                        id = "evt_001",
                        iconColor = "#FF6B9D",
                        startTime = "9am",
                        endTime = "11am",
                        startDateTime = "2024-03-15T09:00:00Z",
                        endDateTime = "2024-03-15T11:00:00Z",
                        title = "FIT3155: Lecture",
                        subtitle = "S4, Clayton"
                    ),
                    LocalDashboardItem.LocalSession(
                        id = "evt_002",
                        iconColor = "#FF6B9D",
                        startTime = "2pm",
                        endTime = "4pm",
                        startDateTime = "2024-03-15T14:00:00Z",
                        endDateTime = "2024-03-15T16:00:00Z",
                        title = "FIT2001: Workshop",
                        subtitle = "Building 5, Clayton"
                    )
                )
            )
        )
    )

    /**
     * Dashboard with only tasks
     */
    fun getTasksOnlyDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Sam",
        sections = listOf(
            LocalDashboardSection(
                header = "Today, 16 March",
                headerType = "date",
                date = "2024-03-16",
                items = listOf(
                    LocalDashboardItem.LocalTask(
                        id = "task_001",
                        iconColor = "#FFB86C",
                        time = "11.59pm",
                        dateTime = "2024-03-16T23:59:00Z",
                        title = "FIT3155: Assignment 3",
                        subtitle = "Not submitted",
                        status = "pending"
                    ),
                    LocalDashboardItem.LocalTask(
                        id = "task_002",
                        iconColor = "#BD93F9",
                        time = "5pm",
                        dateTime = "2024-03-16T17:00:00Z",
                        title = "FIT2001: Quiz",
                        subtitle = "Submitted",
                        status = "submitted"
                    )
                )
            )
        )
    )

    /**
     * Empty dashboard
     */
    fun getEmptyDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Guest",
        sections = emptyList()
    )

    /**
     * Dashboard with parking only
     */
    fun getParkingOnlyDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Visitor",
        sections = listOf(
            LocalDashboardSection(
                header = "Available parking spots",
                headerType = "section",
                items = listOf(
                    LocalDashboardItem.LocalParking(
                        id = "parking_001",
                        title = "Campus Center",
                        badges = listOf(
                            LocalParkingBadge(label = "B", value = 45, color = "#5B9EFF"),
                            LocalParkingBadge(label = "R", value = 23, color = "#FF5757")
                        ),
                        lastUpdated = "2024-03-10T10:00:00Z"
                    )
                )
            )
        )
    )

    /**
     * Dashboard with full parking (0 spots available)
     */
    fun getFullParkingDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Driver",
        sections = listOf(
            LocalDashboardSection(
                header = "Available parking spots",
                headerType = "section",
                items = listOf(
                    LocalDashboardItem.LocalParking(
                        id = "parking_001",
                        title = "North (multi-level)",
                        badges = listOf(
                            LocalParkingBadge(label = "B", value = 0, color = "#5B9EFF"),
                            LocalParkingBadge(label = "R", value = 0, color = "#FF5757")
                        ),
                        lastUpdated = "2024-03-10T10:00:00Z"
                    )
                )
            )
        )
    )

    /**
     * Busy day dashboard with many items
     */
    fun getBusyDayDashboard(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Busy Student",
        sections = listOf(
            LocalDashboardSection(
                header = "Today, 20 March",
                headerType = "date",
                date = "2024-03-20",
                items = listOf(
                    LocalDashboardItem.LocalSession(
                        id = "evt_001",
                        iconColor = "#FF6B9D",
                        startTime = "9am",
                        endTime = "11am",
                        startDateTime = "2024-03-20T09:00:00Z",
                        endDateTime = "2024-03-20T11:00:00Z",
                        title = "FIT3155: Lecture",
                        subtitle = "S4, Clayton"
                    ),
                    LocalDashboardItem.LocalSession(
                        id = "evt_002",
                        iconColor = "#FF6B9D",
                        startTime = "12pm",
                        endTime = "2pm",
                        startDateTime = "2024-03-20T12:00:00Z",
                        endDateTime = "2024-03-20T14:00:00Z",
                        title = "FIT2001: Tutorial",
                        subtitle = "Building 5"
                    ),
                    LocalDashboardItem.LocalTask(
                        id = "task_001",
                        iconColor = "#FFB86C",
                        time = "3pm",
                        dateTime = "2024-03-20T15:00:00Z",
                        title = "MTK1000: Quiz",
                        subtitle = "Not submitted",
                        status = "pending"
                    ),
                    LocalDashboardItem.LocalSession(
                        id = "evt_003",
                        iconColor = "#FF6B9D",
                        startTime = "5pm",
                        endTime = "7pm",
                        startDateTime = "2024-03-20T17:00:00Z",
                        endDateTime = "2024-03-20T19:00:00Z",
                        title = "FIT3077: Workshop",
                        subtitle = "Online"
                    )
                )
            )
        )
    )
}

