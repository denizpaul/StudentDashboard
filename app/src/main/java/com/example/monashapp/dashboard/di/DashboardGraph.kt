package com.example.monashapp.dashboard.di

import com.example.monashapp.dashboard.data.local.DashboardLocalDataSource
import com.example.monashapp.dashboard.data.local.FakeDashboardLocalDataSource
import com.example.monashapp.dashboard.data.repository.DashboardRepositoryImpl
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase

object DashboardGraph {
    private lateinit var localDataSource: DashboardLocalDataSource
    private lateinit var repository: DashboardRepository

    fun initialize() {
        localDataSource = FakeDashboardLocalDataSource()
        repository = DashboardRepositoryImpl(localDataSource)
    }

    fun provideGetDashboardDataUseCase(): GetDashboardDataUseCase {
        check(::repository.isInitialized) { "DashboardGraph is not initialized" }
        return GetDashboardDataUseCase(repository)
    }
}