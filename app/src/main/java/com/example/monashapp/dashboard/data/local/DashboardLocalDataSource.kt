package com.example.monashapp.dashboard.data.local

import com.example.monashapp.dashboard.data.model.LocalDashboardPayload
import kotlinx.coroutines.flow.Flow

interface DashboardLocalDataSource {
    fun observeDashboard(): Flow<LocalDashboardPayload>
}
