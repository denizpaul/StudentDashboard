package com.example.monashapp.dashboard.data.local

import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.ParkingLotEntity
import com.example.monashapp.dashboard.data.model.SessionCategoryEntity
import com.example.monashapp.dashboard.data.model.TaskEntity
import com.example.monashapp.dashboard.data.model.TaskStatusEntity
import com.example.monashapp.dashboard.data.model.TodaySessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeDashboardLocalDataSource : DashboardLocalDataSource {
    private val payload = MutableStateFlow(createPayload())

    override fun observeDashboard(): Flow<LocalDashboardPayload> = payload.asStateFlow()

    private fun createPayload(): LocalDashboardPayload = LocalDashboardPayload(
        greeting = "Hey, Kier",
        dateLabel = "Today, 10 March",
        todaySessions = listOf(
            TodaySessionEntity(
                id = "session_1",
                startTime = "10.30am",
                endTime = "1.30pm",
                title = "FIT2001: Tutorial",
                subtitle = "S4, 13 College Walk, Clayton",
                category = SessionCategoryEntity.CLASS
            ),
            TodaySessionEntity(
                id = "session_2",
                startTime = "5pm",
                endTime = null,
                title = "MTK1000: Weekly quizzes",
                subtitle = "Submitted",
                category = SessionCategoryEntity.ASSIGNMENT
            )
        ),
        upcomingLabel = "Sun, 12 March",
        upcomingTasks = listOf(
            TaskEntity(
                id = "task_1",
                dueTime = "5pm",
                title = "FIT2050: In-class quizzes submission closes",
                subtitle = "Not submitted",
                status = TaskStatusEntity.NOT_SUBMITTED
            )
        ),
        parkingLots = listOf(
            ParkingLotEntity(id = "parking_1", zoneName = "North (multi-level)", bluePermit = 12, redPermit = 5),
            ParkingLotEntity(id = "parking_2", zoneName = "West 1", bluePermit = 0, redPermit = 2)
        )
    )
}