package com.example.monashapp.dashboard.domain.repository

import com.example.monashapp.core.model.dashboard.DashboardData
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun observeDashboard(): Flow<DashboardData>
}

