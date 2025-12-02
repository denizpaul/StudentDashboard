package com.example.monashapp.dashboard.presentation

sealed class DashboardUiEvent {
    data object OnRefresh : DashboardUiEvent()
}
