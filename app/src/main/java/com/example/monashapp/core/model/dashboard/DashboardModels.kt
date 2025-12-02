package com.example.monashapp.core.model.dashboard

/**
 * Domain-level representation of the dashboard surface.
 */
data class DashboardData(
    val greeting: String,
    val dateLabel: String,
    val todaySessions: List<TodaySession>,
    val upcomingLabel: String,
    val upcomingTasks: List<UpcomingTask>,
    val parkingAvailability: List<ParkingAvailability>
)

data class TodaySession(
    val id: String,
    val startTime: String,
    val endTime: String?,
    val title: String,
    val subtitle: String,
    val category: SessionCategory
)

data class UpcomingTask(
    val id: String,
    val dueTime: String,
    val title: String,
    val subtitle: String,
    val status: TaskStatus
)

data class ParkingAvailability(
    val id: String,
    val zoneName: String,
    val bluePermitAvailable: Int,
    val redPermitAvailable: Int
)

enum class SessionCategory {
    CLASS,
    ASSIGNMENT
}

enum class TaskStatus {
    NOT_SUBMITTED,
    SUBMITTED
}
