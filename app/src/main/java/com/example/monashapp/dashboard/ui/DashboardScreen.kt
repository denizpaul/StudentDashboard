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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.DashboardSection
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.core.model.dashboard.ParkingBadge
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.dashboard.presentation.DashboardUiState
import com.example.monashapp.dashboard.ui.components.CardTile
import com.example.monashapp.dashboard.ui.components.DataPoint
import com.example.monashapp.dashboard.ui.components.EventCell
import com.example.monashapp.dashboard.ui.components.EventIcon
import com.example.monashapp.dashboard.ui.components.EventTime
import com.example.monashapp.dashboard.ui.components.SectionTitle
import com.example.monashapp.dashboard.ui.components.SmallCell
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors
import com.example.monashapp.ui.preview.PreviewFontScales
import com.example.monashapp.ui.preview.PreviewLandscape
import com.example.monashapp.ui.preview.PreviewLightDark

@Composable
fun DashboardScreen(
    uiState: DashboardUiState
) {
    DashboardContent(
        state = uiState,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun DashboardContent(state: DashboardUiState, modifier: Modifier = Modifier) {
    val dashboardColors = MaterialTheme.dashboardColors

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = DashboardSpacing.screenHorizontal,
            top = DashboardSpacing.screenTop,
            end = DashboardSpacing.screenHorizontal,
            bottom = DashboardSpacing.screenBottom
        ),
        verticalArrangement = Arrangement.spacedBy(DashboardSpacing.cardSpacing)
    ) {
        item(key = "greeting") {
            Text(
                text = state.greeting,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start,
                modifier = Modifier.semantics { heading() } // Mark greeting as main heading
            )
        }

        items(
            items = state.sections,
            key = { section -> section.header }
        ) { section ->
            when (section.headerType) {
                HeaderType.DATE -> {
                    Card(
                        shape = RoundedCornerShape(DashboardSpacing.cardCorner),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(DashboardSpacing.cardPadding),
                            verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)
                        ) {
                            CardTile(title = section.header)

                            section.items.forEachIndexed { index, item ->
                                when (item) {
                                    is DashboardItem.Session -> {
                                        SessionItemCell(item = item)
                                    }
                                    is DashboardItem.Task -> {
                                        TaskItemCell(item = item)
                                    }
                                    else -> {}
                                }
                                if (index != section.items.lastIndex) {
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
                HeaderType.SECTION -> {
                    SectionTitle(
                        title = section.header,
                        showDivider = true
                    )
                    Card(
                        shape = RoundedCornerShape(DashboardSpacing.cardCorner),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(DashboardSpacing.cardPadding)) {
                            section.items.forEach { item ->
                                when (item) {
                                    is DashboardItem.Parking -> {
                                        ParkingItemCell(item = item)
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val isPreview = LocalInspectionMode.current
    val dashboardColors = MaterialTheme.dashboardColors

    val color = remember(item.iconColor, isPreview) {
        if (isPreview) {
            dashboardColors.sessionClassIndicator
        } else {
            try {
                val androidColor = android.graphics.Color.parseColor(item.iconColor)
                Color(androidColor)
            } catch (_: Exception) {
                dashboardColors.sessionClassIndicator
            }
        }
    }

    EventCell(
        icon = EventIcon.DurationLine(color),
        time = EventTime.Range(item.startTime, item.endTime),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = "Class session" // Accessibility description
    )
}

@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    val isPreview = LocalInspectionMode.current
    val dashboardColors = MaterialTheme.dashboardColors

    val color = remember(item.iconColor, isPreview) {
        if (isPreview) {
            dashboardColors.taskBadge
        } else {
            try {
                val androidColor = android.graphics.Color.parseColor(item.iconColor)
                Color(androidColor)
            } catch (_: Exception) {
                dashboardColors.taskBadge
            }
        }
    }

    EventCell(
        icon = EventIcon.TaskCircle(
            color = color,
            iconRes = R.drawable.ic_task
        ),
        time = EventTime.Single(item.time),
        title = item.title,
        subtitle = item.subtitle,
        iconDescription = "Task" // Accessibility description
    )
}

@Composable
private fun ParkingItemCell(item: DashboardItem.Parking) {
    val isPreview = LocalInspectionMode.current
    val primaryColor = MaterialTheme.colorScheme.primary

    val dataPoints = remember(item.badges, isPreview) {
        item.badges.map { badge ->
            val color = if (isPreview) {
                primaryColor
            } else {
                try {
                    val androidColor = android.graphics.Color.parseColor(badge.color)
                    Color(androidColor)
                } catch (_: Exception) {
                    primaryColor
                }
            }
            DataPoint(
                label = badge.label,
                value = badge.value,
                color = color
            )
        }
    }

    SmallCell(
        title = item.title,
        dataPoints = dataPoints
    )
}

private val previewDashboardState = DashboardUiState(
    greeting = "Hey, Kier",
    sections = listOf(
        DashboardSection(
            header = "Today • Tue, 10 March",
            headerType = HeaderType.DATE,
            date = "Tue, 10 March",
            items = listOf(
                DashboardItem.Session(
                    id = "session-1",
                    iconColor = "#6750A4",
                    startTime = "09:00",
                    endTime = "10:30",
                    startDateTime = "2024-03-10T09:00:00",
                    endDateTime = "2024-03-10T10:30:00",
                    title = "FIT2099: Studio Workshop",
                    subtitle = "Building inclusive data-driven apps"
                ),
                DashboardItem.Session(
                    id = "session-2",
                    iconColor = "#386A20",
                    startTime = "11:00",
                    endTime = "12:00",
                    startDateTime = "2024-03-10T11:00:00",
                    endDateTime = "2024-03-10T12:00:00",
                    title = "LAW1010: Guest lecture",
                    subtitle = "Legal frameworks for AI"
                ),
                DashboardItem.Task(
                    id = "task-1",
                    iconColor = "#FFB300",
                    time = "17:00",
                    dateTime = "2024-03-10T17:00:00",
                    title = "MTK1000: Weekly quizzes",
                    subtitle = "Due today",
                    status = TaskStatus.PENDING
                )
            )
        ),
        DashboardSection(
            header = "Parking availability",
            headerType = HeaderType.SECTION,
            items = listOf(
                DashboardItem.Parking(
                    id = "parking-1",
                    title = "Clayton • North multi-level",
                    badges = listOf(
                        ParkingBadge(label = "Blue", value = 24, color = "#2962FF"),
                        ParkingBadge(label = "Red", value = 12, color = "#D32F2F")
                    ),
                    lastUpdated = "Updated 2 mins ago"
                )
            )
        )
    )
)

private val previewEmptyDashboardState = DashboardUiState(
    greeting = "Hey, Kier",
    sections = emptyList()
)

@PreviewLightDark
@Composable
private fun DashboardScreenPopulatedPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState)
    }
}

@PreviewFontScales
@Composable
private fun DashboardScreenAccessibilityPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState)
    }
}

@PreviewLandscape
@Composable
private fun DashboardScreenLandscapePreview() {
    MonashTheme {
        DashboardScreen(uiState = previewDashboardState)
    }
}

@Preview(
    name = "Dashboard - Empty",
    showBackground = true
)
@Composable
private fun DashboardScreenEmptyPreview() {
    MonashTheme {
        DashboardScreen(uiState = previewEmptyDashboardState)
    }
}
