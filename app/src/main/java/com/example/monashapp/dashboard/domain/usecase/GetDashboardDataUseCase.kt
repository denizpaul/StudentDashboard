package com.example.monashapp.dashboard.domain.usecase

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow

class GetDashboardDataUseCase(
    private val repository: DashboardRepository
) {
    operator fun invoke(): Flow<DashboardData> = repository.observeDashboard()
}

