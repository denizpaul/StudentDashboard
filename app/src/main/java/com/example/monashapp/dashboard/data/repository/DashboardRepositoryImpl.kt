package com.example.monashapp.dashboard.data.repository

import com.example.monashapp.core.model.dashboard.DashboardData
import com.example.monashapp.dashboard.data.local.DashboardLocalDataSource
import com.example.monashapp.dashboard.data.mapper.toDomain
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardRepositoryImpl(
    private val localDataSource: DashboardLocalDataSource
) : DashboardRepository {
    override fun observeDashboard(): Flow<DashboardData> =
        localDataSource.observeDashboard().map { it.toDomain() }
}

