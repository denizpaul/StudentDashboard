package com.example.monashapp.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.monashapp.dashboard.di.DashboardGraph
import com.example.monashapp.dashboard.presentation.DashboardUiEvent
import com.example.monashapp.dashboard.presentation.DashboardViewModel

@Composable
fun DashboardRoute(viewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory)) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

private val DashboardViewModelFactory = viewModelFactory {
    initializer {
        DashboardViewModel(
            getDashboardData = DashboardGraph.provideGetDashboardDataUseCase()
        )
    }
}
