package com.example.monashapp.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.preview.PreviewComplete
import com.example.monashapp.ui.preview.PreviewLightDark
import com.example.monashapp.ui.preview.PreviewScreenSizes
import com.example.monashapp.ui.theme.MonashTheme

/**
 * Component Sticker Sheet - Dashboard Components
 *
 * This file serves as a visual documentation library showcasing all dashboard components
 * in various states. It helps designers and developers quickly see all component variations.
 */

@PreviewLightDark
@Composable
private fun ComponentStickerSheet() {
    MonashTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Section: Toolbar
                SectionHeader(title = "Toolbar")
                DashboardToolbar(title = "Hey, Kier")

                // Section: Session Cards
                SectionHeader(title = "Session Cards")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TodaySessionCard(
                            session = TodaySession(
                                id = "1",
                                startTime = "10.30am",
                                endTime = "1.30pm",
                                title = "FIT2001: Tutorial",
                                subtitle = "S4, 13 College Walk, Clayton",
                                category = SessionCategory.CLASS
                            )
                        )
                        TodaySessionCard(
                            session = TodaySession(
                                id = "2",
                                startTime = "5pm",
                                endTime = null,
                                title = "MTK1000: Weekly quizzes",
                                subtitle = "Submitted",
                                category = SessionCategory.ASSIGNMENT
                            )
                        )
                    }
                }

                // Section: Upcoming Tasks
                SectionHeader(title = "Upcoming Tasks")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        UpcomingTasksCard(
                            label = "Sun, 12 March",
                            tasks = listOf(
                                UpcomingTask(
                                    id = "1",
                                    dueTime = "5pm",
                                    title = "FIT2050: In-class quizzes submission closes",
                                    subtitle = "Not submitted",
                                    status = TaskStatus.NOT_SUBMITTED
                                ),
                                UpcomingTask(
                                    id = "2",
                                    dueTime = "11.59pm",
                                    title = "FIT3155: Assignment 2",
                                    subtitle = "Submitted",
                                    status = TaskStatus.SUBMITTED
                                )
                            )
                        )
                    }
                }

                // Section: Parking Availability
                SectionHeader(title = "Parking Availability")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ParkingAvailabilityList(
                            parkingAvailability = listOf(
                                ParkingAvailability(
                                    id = "1",
                                    zoneName = "North (multi-level)",
                                    bluePermitAvailable = 12,
                                    redPermitAvailable = 5
                                ),
                                ParkingAvailability(
                                    id = "2",
                                    zoneName = "West 1",
                                    bluePermitAvailable = 0,
                                    redPermitAvailable = 2
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

/**
 * Focused previews for individual component states
 */

@Preview(
    name = "All Session Types",
    group = "Component Gallery",
    showBackground = true
)
@Composable
private fun SessionTypesGallery() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)) {
                TodaySessionCard(
                    session = TodaySession(
                        id = "class",
                        startTime = "10.30am",
                        endTime = "1.30pm",
                        title = "CLASS Category",
                        subtitle = "With vertical indicator",
                        category = SessionCategory.CLASS
                    )
                )
                TodaySessionCard(
                    session = TodaySession(
                        id = "assignment",
                        startTime = "5pm",
                        endTime = null,
                        title = "ASSIGNMENT Category",
                        subtitle = "With circular badge",
                        category = SessionCategory.ASSIGNMENT
                    )
                )
            }
        }
    }
}

@Preview(
    name = "Task Status Variations",
    group = "Component Gallery",
    showBackground = true
)
@Composable
private fun TaskStatusGallery() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "All Task States",
                tasks = listOf(
                    UpcomingTask(
                        id = "1",
                        dueTime = "5pm",
                        title = "Not Submitted Task",
                        subtitle = "Not submitted",
                        status = TaskStatus.NOT_SUBMITTED
                    ),
                    UpcomingTask(
                        id = "2",
                        dueTime = "11.59pm",
                        title = "Submitted Task",
                        subtitle = "Submitted",
                        status = TaskStatus.SUBMITTED
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Parking States",
    group = "Component Gallery",
    showBackground = true
)
@Composable
private fun ParkingStatesGallery() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Various Parking Availability States:", style = MaterialTheme.typography.labelMedium)
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ParkingAvailabilityList(
                            parkingAvailability = listOf(
                                ParkingAvailability("1", "High Availability", bluePermitAvailable = 150, redPermitAvailable = 89),
                                ParkingAvailability("2", "Medium Availability", bluePermitAvailable = 12, redPermitAvailable = 5),
                                ParkingAvailability("3", "Low Availability", bluePermitAvailable = 2, redPermitAvailable = 1),
                                ParkingAvailability("4", "Full (No Spots)", bluePermitAvailable = 0, redPermitAvailable = 0)
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Edge case and error state previews
 */

@Preview(
    name = "Empty States",
    group = "Edge Cases",
    showBackground = true
)
@Composable
private fun EmptyStatesPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        UpcomingTasksCard(label = "No Tasks Today", tasks = emptyList())
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Long Content",
    group = "Edge Cases",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LongContentPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardToolbar(title = "Hey, Christopher Alexander Jonathan")

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TodaySessionCard(
                            session = TodaySession(
                                id = "long",
                                startTime = "10.30am",
                                endTime = "1.30pm",
                                title = "FIT3155: Advanced Algorithms and Data Structures - Special Topics Seminar",
                                subtitle = "Building 123, Room 456, Very Long Campus Name Street, Clayton Campus, Victoria",
                                category = SessionCategory.CLASS
                            )
                        )
                    }
                }
            }
        }
    }
}

