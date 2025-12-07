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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.core.model.dashboard.DashboardItem
import com.example.monashapp.core.model.dashboard.HeaderType
import com.example.monashapp.dashboard.presentation.DashboardUiEvent
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

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit
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
        item {
            Text(
                text = state.greeting,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )
        }

        state.sections.forEach { section ->
            when (section.headerType) {
                HeaderType.DATE -> {
                    item {
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
                }
                HeaderType.SECTION -> {
                    item {
                        SectionTitle(
                            title = section.header,
                            showDivider = true
                        )
                    }
                    item {
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
}

@Composable
private fun SessionItemCell(item: DashboardItem.Session) {
    val color = try {
        val androidColor = android.graphics.Color.parseColor(item.iconColor)
        Color(androidColor)
    } catch (e: Exception) {
        MaterialTheme.dashboardColors.sessionClassIndicator
    }

    EventCell(
        icon = EventIcon.DurationLine(color),
        time = EventTime.Range(item.startTime, item.endTime),
        title = item.title,
        subtitle = item.subtitle
    )
}

@Composable
private fun TaskItemCell(item: DashboardItem.Task) {
    val color = try {
        val androidColor = android.graphics.Color.parseColor(item.iconColor)
        Color(androidColor)
    } catch (e: Exception) {
        MaterialTheme.dashboardColors.taskBadge
    }

    EventCell(
        icon = EventIcon.TaskCircle(
            color = color,
            iconRes = R.drawable.ic_task
        ),
        time = EventTime.Single(item.time),
        title = item.title,
        subtitle = item.subtitle
    )
}

@Composable
private fun ParkingItemCell(item: DashboardItem.Parking) {
    val dataPoints = item.badges.map { badge ->
        val color = try {
            val androidColor = android.graphics.Color.parseColor(badge.color)
            Color(androidColor)
        } catch (e: Exception) {
            MaterialTheme.colorScheme.primary
        }
        DataPoint(
            label = badge.label,
            value = badge.value,
            color = color
        )
    }

    SmallCell(
        title = item.title,
        dataPoints = dataPoints
    )
}

@Preview(
    name = "Dashboard - Light",
    showBackground = true
)
@Composable
private fun DashboardScreenPreview() {
    MonashTheme {
        DashboardScreen(
            uiState = DashboardUiState(greeting = "Hey, Kier", sections = emptyList()),
            onEvent = {}
        )
    }
}
