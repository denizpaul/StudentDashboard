package com.example.monashapp.dashboard.data.model

data class LocalDashboardPayload(
    val greeting: String,
    val sections: List<LocalDashboardSection>
)

data class LocalDashboardSection(
    val header: String,
    val headerType: String,
    val date: String? = null,
    val items: List<LocalDashboardItem>
)

sealed class LocalDashboardItem {
    data class LocalSession(
        val id: String,
        val iconColor: String,
        val startTime: String,
        val endTime: String,
        val startDateTime: String,
        val endDateTime: String,
        val title: String,
        val subtitle: String
    ) : LocalDashboardItem()

    data class LocalTask(
        val id: String,
        val iconColor: String,
        val time: String,
        val dateTime: String,
        val title: String,
        val subtitle: String,
        val status: String
    ) : LocalDashboardItem()

    data class LocalParking(
        val id: String,
        val title: String,
        val badges: List<LocalParkingBadge>,
        val lastUpdated: String
    ) : LocalDashboardItem()
}

data class LocalParkingBadge(
    val label: String,
    val value: Int,
    val color: String
)
