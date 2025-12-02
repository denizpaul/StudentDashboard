package com.example.monashapp.dashboard.data.mapper

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.data.model.ParkingLotEntity
import com.example.monashapp.dashboard.data.model.SessionCategoryEntity
import com.example.monashapp.dashboard.data.model.TaskEntity
import com.example.monashapp.dashboard.data.model.TaskStatusEntity
import com.example.monashapp.dashboard.data.model.TodaySessionEntity

fun LocalDashboardPayload.toDomain(): DashboardData = DashboardData(
    greeting = greeting,
    dateLabel = dateLabel,
    todaySessions = todaySessions.map(TodaySessionEntity::toDomain),
    upcomingLabel = upcomingLabel,
    upcomingTasks = upcomingTasks.map(TaskEntity::toDomain),
    parkingAvailability = parkingLots.map(ParkingLotEntity::toDomain)
)

private fun TodaySessionEntity.toDomain(): TodaySession = TodaySession(
    id = id,
    startTime = startTime,
    endTime = endTime,
    title = title,
    subtitle = subtitle,
    category = category.toDomain()
)

private fun TaskEntity.toDomain(): UpcomingTask = UpcomingTask(
    id = id,
    dueTime = dueTime,
    title = title,
    subtitle = subtitle,
    status = status.toDomain()
)

private fun ParkingLotEntity.toDomain(): ParkingAvailability = ParkingAvailability(
    id = id,
    zoneName = zoneName,
    bluePermitAvailable = bluePermit,
    redPermitAvailable = redPermit
)

private fun SessionCategoryEntity.toDomain(): SessionCategory = when (this) {
    SessionCategoryEntity.CLASS -> SessionCategory.CLASS
    SessionCategoryEntity.ASSIGNMENT -> SessionCategory.ASSIGNMENT
}

private fun TaskStatusEntity.toDomain(): TaskStatus = when (this) {
    TaskStatusEntity.NOT_SUBMITTED -> TaskStatus.NOT_SUBMITTED
    TaskStatusEntity.SUBMITTED -> TaskStatus.SUBMITTED
}
