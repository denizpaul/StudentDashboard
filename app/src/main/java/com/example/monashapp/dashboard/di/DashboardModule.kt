package com.example.monashapp.dashboard.di

import com.example.monashapp.dashboard.data.local.DashboardLocalDataSource
import com.example.monashapp.dashboard.data.local.FakeDashboardLocalDataSource
import com.example.monashapp.dashboard.data.repository.DashboardRepositoryImpl
import com.example.monashapp.dashboard.domain.repository.DashboardRepository
import com.example.monashapp.dashboard.domain.usecase.GetDashboardDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(): DashboardLocalDataSource = FakeDashboardLocalDataSource()

    @Provides
    @Singleton
    fun provideRepository(localDataSource: DashboardLocalDataSource): DashboardRepository =
        DashboardRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideGetDashboardDataUseCase(repository: DashboardRepository): GetDashboardDataUseCase =
        GetDashboardDataUseCase(repository)
}
