package com.example.monashapp.dashboard.data.mapper

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.DashboardSection
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.ParkingBadge
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.data.model.LocalDashboardItem
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.LocalDashboardSection
import com.example.monashapp.dashboard.data.model.LocalParkingBadge

fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(
    greeting = greeting,
    sections = sections.map { it.toDomain() }
)

fun LocalDashboardSection.toDomain(): DashboardSection = DashboardSection(
    header = header,
    headerType = when (headerType.lowercase()) {
        "date" -> HeaderType.DATE
        "section" -> HeaderType.SECTION
        else -> HeaderType.SECTION
    },
    date = date,
    items = items.map { it.toDomain() }
)

fun LocalDashboardItem.toDomain(): DashboardItem = when (this) {
    is LocalDashboardItem.LocalSession -> DashboardItem.Session(
        id = id,
        iconColor = iconColor,
        startTime = startTime,
        endTime = endTime,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        title = title,
        subtitle = subtitle
    )
    is LocalDashboardItem.LocalTask -> DashboardItem.Task(
        id = id,
        iconColor = iconColor,
        time = time,
        dateTime = dateTime,
        title = title,
        subtitle = subtitle,
        status = when (status.lowercase()) {
            "submitted" -> TaskStatus.SUBMITTED
            "pending" -> TaskStatus.PENDING
            else -> TaskStatus.PENDING
        }
    )
    is LocalDashboardItem.LocalParking -> DashboardItem.Parking(
        id = id,
        title = title,
        badges = badges.map { it.toDomain() },
        lastUpdated = lastUpdated
    )
}

fun LocalParkingBadge.toDomain(): ParkingBadge = ParkingBadge(
    label = label,
    value = value,
    color = color
)
