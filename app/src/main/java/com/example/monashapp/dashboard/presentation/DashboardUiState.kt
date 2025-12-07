package com.example.monashapp.dashboard.presentation

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.core.model.dashboard.DashboardSection

data class DashboardUiState(
    val greeting: String = "",
    val sections: List<DashboardSection> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun DashboardData.toUiState(): DashboardUiState = DashboardUiState(
    greeting = greeting,
    sections = sections
)