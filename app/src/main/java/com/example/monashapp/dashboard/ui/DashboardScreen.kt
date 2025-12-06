package com.example.monashapp.dashboard.ui

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

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
    val dashboardColors = MaterialTheme.dashboardColors

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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(DashboardSpacing.cardPadding), verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)) {
                    state.todaySessions.forEachIndexed { index, session ->
                        TodaySessionCard(session)
                        if (index != state.todaySessions.lastIndex) {
                            Spacer(
                                modifier = Modifier
                                    .height(DashboardSpacing.dividerThickness)
                                    .fillMaxWidth()
                                    .background(dashboardColors.divider)
                            )
                        }
                    }
                }
            }
        }
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(DashboardSpacing.cardPadding)) {
                    ParkingAvailabilityList(parkingAvailability = state.parkingAvailability)
                }
            }
        }
    }
}

// Preview parameter provider for different dashboard states
private class DashboardStateProvider : PreviewParameterProvider<DashboardUiState> {
    override val values = sequenceOf(
        // Typical state
        previewDashboardState,
        // Busy day - many sessions and tasks
        DashboardUiState(
            greeting = "Hey, Alex",
            dateLabel = "Today, 15 March",
            todaySessions = listOf(
                TodaySession("1", "9am", "11am", "FIT3155: Lecture", "S4, Clayton", SessionCategory.CLASS),
                TodaySession("2", "12pm", "2pm", "FIT2001: Tutorial", "Building 5", SessionCategory.CLASS),
                TodaySession("3", "3pm", null, "MTK1000: Quiz", "Submitted", SessionCategory.ASSIGNMENT),
                TodaySession("4", "5pm", "7pm", "FIT3077: Workshop", "Online", SessionCategory.CLASS)
            ),
            upcomingLabel = "Tomorrow",
            upcomingTasks = listOf(
                UpcomingTask("1", "11.59pm", "FIT3155: Assignment 3", "Not submitted", TaskStatus.NOT_SUBMITTED),
                UpcomingTask("2", "5pm", "FIT2001: Quiz", "Not submitted", TaskStatus.NOT_SUBMITTED)
            ),
            parkingAvailability = listOf(
                ParkingAvailability("1", "North", 0, 0),
                ParkingAvailability("2", "South", 2, 1)
            ),
            isLoading = false
        ),
        // Light day - minimal content
        DashboardUiState(
            greeting = "Hey, Sam",
            dateLabel = "Today, 16 March",
            todaySessions = listOf(
                TodaySession("1", "2pm", "4pm", "FIT2001: Tutorial", "Clayton", SessionCategory.CLASS)
            ),
            upcomingLabel = "Sun, 17 March",
            upcomingTasks = emptyList(),
            parkingAvailability = listOf(
                ParkingAvailability("1", "Campus Center", 45, 23)
            ),
            isLoading = false
        )
    )
}

@Preview(
    name = "Dashboard - Light",
    group = "DashboardScreen",
    showBackground = true,
    device = "id:pixel_5"
)
@Preview(
    name = "Dashboard - Dark",
    group = "DashboardScreen",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
private fun DashboardScreenPreview(
    @PreviewParameter(DashboardStateProvider::class, limit = 1) state: DashboardUiState
) {
    MonashTheme {
        DashboardScreen(uiState = state, onEvent = {})
    }
}

@Preview(
    name = "Dashboard - Typical Day",
    group = "DashboardScreen - States",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
private fun DashboardScreenTypicalPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}

@Preview(
    name = "Dashboard - Busy Day",
    group = "DashboardScreen - States",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
private fun DashboardScreenBusyPreview() {
    MonashTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                greeting = "Hey, Alex",
                dateLabel = "Today, 15 March",
                todaySessions = listOf(
                    TodaySession("1", "9am", "11am", "FIT3155: Lecture", "S4, Clayton", SessionCategory.CLASS),
                    TodaySession("2", "12pm", "2pm", "FIT2001: Tutorial", "Building 5", SessionCategory.CLASS),
                    TodaySession("3", "3pm", null, "MTK1000: Quiz", "Submitted", SessionCategory.ASSIGNMENT),
                    TodaySession("4", "5pm", "7pm", "FIT3077: Workshop", "Online", SessionCategory.CLASS)
                ),
                upcomingLabel = "Tomorrow",
                upcomingTasks = listOf(
                    UpcomingTask("1", "11.59pm", "FIT3155: Assignment 3", "Not submitted", TaskStatus.NOT_SUBMITTED),
                    UpcomingTask("2", "5pm", "FIT2001: Quiz", "Not submitted", TaskStatus.NOT_SUBMITTED)
                ),
                parkingAvailability = listOf(
                    ParkingAvailability("1", "North", 0, 0),
                    ParkingAvailability("2", "South", 2, 1)
                ),
                isLoading = false
            ),
            onEvent = {}
        )
    }
}

@Preview(
    name = "Dashboard - Light Day",
    group = "DashboardScreen - States",
    showBackground = true,
    device = "id:pixel_5"
)
@Composable
private fun DashboardScreenLightDayPreview() {
    MonashTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                greeting = "Hey, Sam",
                dateLabel = "Today, 16 March",
                todaySessions = listOf(
                    TodaySession("1", "2pm", "4pm", "FIT2001: Tutorial", "Clayton", SessionCategory.CLASS)
                ),
                upcomingLabel = "Sun, 17 March",
                upcomingTasks = emptyList(),
                parkingAvailability = listOf(
                    ParkingAvailability("1", "Campus Center", 45, 23)
                ),
                isLoading = false
            ),
            onEvent = {}
        )
    }
}

@Preview(
    name = "Dashboard - Small Screen",
    group = "DashboardScreen - Screen Sizes",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Composable
private fun DashboardScreenSmallPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}

@Preview(
    name = "Dashboard - Tablet",
    group = "DashboardScreen - Screen Sizes",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
@Composable
private fun DashboardScreenTabletPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}

@Preview(
    name = "Dashboard - Accessibility (Large Font)",
    group = "DashboardScreen",
    showBackground = true,
    fontScale = 1.5f,
    device = "id:pixel_5"
)
@Composable
private fun DashboardScreenAccessibilityPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}

@Preview(
    name = "Dashboard - Landscape",
    group = "DashboardScreen - Orientations",
    showBackground = true,
    widthDp = 640,
    heightDp = 360
)
@Composable
private fun DashboardScreenLandscapePreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState, onEvent = {})
    }
}