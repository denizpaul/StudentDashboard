package com.example.monashapp.dashboard.ui

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.presentation.DashboardUiState

internal val previewDashboardState = DashboardUiState(
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
        ),
        TodaySession(
            id = "session_2",
            startTime = "5pm",
            endTime = null,
            title = "MTK1000: Weekly quizzes",
            subtitle = "Submitted",
            category = SessionCategory.ASSIGNMENT
        )
    ),
    upcomingLabel = "Sun, 12 March",
    upcomingTasks = listOf(
        UpcomingTask(
            id = "task_1",
            dueTime = "5pm",
            title = "FIT2050: In-class quizzes submission closes",
            subtitle = "Not submitted",
            status = TaskStatus.NOT_SUBMITTED
        )
    ),
    parkingAvailability = listOf(
        ParkingAvailability(id = "parking_1", zoneName = "North (multi-level)", bluePermitAvailable = 12, redPermitAvailable = 5),
        ParkingAvailability(id = "parking_2", zoneName = "West 1", bluePermitAvailable = 0, redPermitAvailable = 2)
    ),
    isLoading = false
)
