package com.example.monashapp.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardData: GetDashboardDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeDashboard()
    }

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            DashboardUiEvent.OnRefresh -> observeDashboard()
        }
    }

    private fun observeDashboard() {
        viewModelScope.launch {
            getDashboardData()
                .collect { data ->
                    _uiState.value = DashboardUiState(
                        greeting = data.greeting,
                        dateLabel = data.dateLabel,
                        todaySessions = data.todaySessions,
                        upcomingLabel = data.upcomingLabel,
                        upcomingTasks = data.upcomingTasks,
                        parkingAvailability = data.parkingAvailability,
                        isLoading = false
                    )
                }
        }
    }
}
