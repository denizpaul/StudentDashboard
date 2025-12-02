package com.example.monashapp.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.dashboard.presentation.DashboardUiState
import com.example.monashapp.dashboard.presentation.DashboardUiEvent
import com.example.monashapp.dashboard.ui.components.DashboardToolbar
import com.example.monashapp.dashboard.ui.components.ParkingAvailabilityList
import com.example.monashapp.dashboard.ui.components.TodaySessionCard
import com.example.monashapp.dashboard.ui.components.UpcomingTasksCard
import androidx.compose.ui.tooling.preview.Preview
import com.example.monashapp.ui.theme.MonashTheme

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit
) {
    Scaffold(
        topBar = { DashboardToolbar(title = uiState.greeting) }
    ) { innerPadding ->
        DashboardContent(
            state = uiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DashboardContent(state: DashboardUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = DashboardSpacing.screenHorizontal, vertical = DashboardSpacing.screenVertical),
        verticalArrangement = Arrangement.spacedBy(DashboardSpacing.sectionSpacing),
        contentPadding = PaddingValues(bottom = DashboardSpacing.sectionSpacing)
    ) {
        item {
            Text(
                text = state.dateLabel,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(DashboardSpacing.cardPadding), verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)) {
                    state.todaySessions.forEachIndexed { index, session ->
                        TodaySessionCard(session)
                        if (index != state.todaySessions.lastIndex) {
                            Spacer(modifier = Modifier.height(DashboardSpacing.dividerThickness).fillMaxWidth().background(DashboardColors.divider))
                        }
                    }
                }
            }
        }
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(DashboardSpacing.cardPadding)) {
                    UpcomingTasksCard(label = state.upcomingLabel, tasks = state.upcomingTasks)
                }
            }
        }
        item {
            Text(
                text = stringResource(id = R.string.dashboard_parking_label),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(DashboardSpacing.cardPadding)) {
                    ParkingAvailabilityList(parkingAvailability = state.parkingAvailability)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}