package com.example.monashapp.dashboard.data.fake

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.dashboard.data.mapper.toDomain
import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Fake repository for UI testing
 * Allows switching between different mock data scenarios
 */
class FakeDashboardRepositoryForUI : DashboardRepository {

    private val _dashboardData = MutableStateFlow<LocalDashboardPayload?>(null)

    fun setMockData(payload: LocalDashboardPayload) {
        _dashboardData.value = payload
    }

    override fun observeDashboard(): Flow<DashboardData> {
        // If no data is set, emit empty
        if (_dashboardData.value == null) {
            _dashboardData.value = LocalDashboardPayload("", emptyList())
        }

        return _dashboardData.map { payload ->
            payload?.toDomain() ?: DashboardData("", emptyList())
        }
    }
}

