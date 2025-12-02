package com.example.monashapp.dashboard.presentation

import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask

data class DashboardUiState(
    val greeting: String = "",
    val dateLabel: String = "",
    val todaySessions: List<TodaySession> = emptyList(),
    val upcomingLabel: String = "",
    val upcomingTasks: List<UpcomingTask> = emptyList(),
    val parkingAvailability: List<ParkingAvailability> = emptyList(),
    val isLoading: Boolean = true
)