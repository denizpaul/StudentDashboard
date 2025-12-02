package com.example.monashapp.dashboard.di

import com.example.monashapp.dashboard.data.local.FakeDashboardLocalDataSource
import com.example.monashapp.dashboard.data.repository.DashboardRepositoryImpl
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase

object DashboardGraph {
    private val localDataSource by lazy { FakeDashboardLocalDataSource() }
    private val repository by lazy { DashboardRepositoryImpl(localDataSource) }

    fun provideGetDashboardDataUseCase(): GetDashboardDataUseCase = GetDashboardDataUseCase(repository)
}

