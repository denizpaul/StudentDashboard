package com.example.monashapp.core.model.dashboard

/**
 * Domain-level representation of the dashboard surface.
 */
data class DashboardData(
    val greeting: String,
    val sections: List<DashboardSection>
)

data class DashboardSection(
    val header: String,
    val headerType: HeaderType,
    val date: String? = null,
    val items: List<DashboardItem>
)

sealed class DashboardItem {
    data class Session(
        val id: String,
        val iconColor: String,
        val startTime: String,
        val endTime: String,
        val startDateTime: String,
        val endDateTime: String,
        val title: String,
        val subtitle: String
    ) : DashboardItem()

    data class Task(
        val id: String,
        val iconColor: String,
        val time: String,
        val dateTime: String,
        val title: String,
        val subtitle: String,
        val status: TaskStatus
    ) : DashboardItem()

    data class Parking(
        val id: String,
        val title: String,
        val badges: List<ParkingBadge>,
        val lastUpdated: String
    ) : DashboardItem()
}

data class ParkingBadge(
    val label: String,
    val value: Int,
    val color: String
)

enum class HeaderType {
    DATE,
    SECTION
}

enum class TaskStatus {
    SUBMITTED,
    PENDING
}