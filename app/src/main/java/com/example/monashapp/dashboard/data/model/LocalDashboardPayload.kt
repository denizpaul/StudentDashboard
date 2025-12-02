package com.example.monashapp.dashboard.data.model

import com.example.monashapp.dashboard.data.model.ParkingLotEntity
import com.example.monashapp.dashboard.data.model.TaskEntity
import com.example.monashapp.dashboard.data.model.TodaySessionEntity

data class LocalDashboardPayload(
    val greeting: String,
    val dateLabel: String,
    val todaySessions: List<TodaySessionEntity>,
    val upcomingLabel: String,
    val upcomingTasks: List<TaskEntity>,
    val parkingLots: List<ParkingLotEntity>
)

data class TodaySessionEntity(
    val id: String,
    val startTime: String,
    val endTime: String?,
    val title: String,
    val subtitle: String,
    val category: SessionCategoryEntity
)

data class TaskEntity(
    val id: String,
    val dueTime: String,
    val title: String,
    val subtitle: String,
    val status: TaskStatusEntity
)

data class ParkingLotEntity(
    val id: String,
    val zoneName: String,
    val bluePermit: Int,
    val redPermit: Int
)

enum class SessionCategoryEntity {
    CLASS,
    ASSIGNMENT
}

enum class TaskStatusEntity {
    NOT_SUBMITTED,
    SUBMITTED
}