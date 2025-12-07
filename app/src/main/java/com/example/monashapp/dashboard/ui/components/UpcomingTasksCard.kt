package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

/**
 * UpcomingTasksCard - Refactored to use CardTile and EventCell components
 *
 * This component uses CardTile for the section header and EventCell for each task
 */
@Composable
fun UpcomingTasksCard(label: String, tasks: List<UpcomingTask>) {
    val dashboardColors = MaterialTheme.dashboardColors

    Column(verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)) {
        // Section header using CardTile
        CardTile(title = label)

        // Each task using EventCell
        tasks.forEach { task ->
            EventCell(
                icon = EventIcon.TaskCircle(dashboardColors.taskBadge),
                time = EventTime.Single(task.dueTime),
                title = task.title,
                subtitle = task.subtitle,
                subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Preview parameter provider for different task list scenarios
private class TaskListProvider : PreviewParameterProvider<Pair<String, List<UpcomingTask>>> {
    override val values = sequenceOf(
        // Single task - not submitted
        "Sun, 12 March" to listOf(
            UpcomingTask(
                id = "task_1",
                dueTime = "5pm",
                title = "FIT2050: In-class quizzes submission closes",
                subtitle = "Not submitted",
                status = TaskStatus.NOT_SUBMITTED
            )
        ),
        // Multiple tasks - mixed status
        "Mon, 13 March" to listOf(
            UpcomingTask(
                id = "task_2",
                dueTime = "11.59pm",
                title = "FIT3155: Assignment 2",
                subtitle = "Not submitted",
                status = TaskStatus.NOT_SUBMITTED
            ),
            UpcomingTask(
                id = "task_3",
                dueTime = "5pm",
                title = "MTK1000: Weekly quiz",
                subtitle = "Submitted",
                status = TaskStatus.SUBMITTED
            )
        ),
        // Empty list edge case
        "Tue, 14 March" to emptyList()
    )
}

@Preview(
    name = "Upcoming Tasks - Light",
    group = "UpcomingTasksCard",
    showBackground = true
)
@Preview(
    name = "Upcoming Tasks - Dark",
    group = "UpcomingTasksCard",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun UpcomingTasksCardPreview(
    @PreviewParameter(TaskListProvider::class) data: Pair<String, List<UpcomingTask>>
) {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(label = data.first, tasks = data.second)
        }
    }
}

@Preview(
    name = "Tasks - Single Not Submitted",
    group = "UpcomingTasksCard - States",
    showBackground = true
)
@Composable
private fun UpcomingTasksCardSinglePreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "Sun, 12 March",
                tasks = listOf(
                    UpcomingTask(
                        id = "task_1",
                        dueTime = "5pm",
                        title = "FIT2050: In-class quizzes submission closes",
                        subtitle = "Not submitted",
                        status = TaskStatus.NOT_SUBMITTED
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Tasks - Multiple Mixed Status",
    group = "UpcomingTasksCard - States",
    showBackground = true
)
@Composable
private fun UpcomingTasksCardMultiplePreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "Mon, 13 March",
                tasks = listOf(
                    UpcomingTask(
                        id = "task_1",
                        dueTime = "11.59pm",
                        title = "FIT3155: Assignment 2",
                        subtitle = "Not submitted",
                        status = TaskStatus.NOT_SUBMITTED
                    ),
                    UpcomingTask(
                        id = "task_2",
                        dueTime = "5pm",
                        title = "MTK1000: Weekly quiz",
                        subtitle = "Submitted",
                        status = TaskStatus.SUBMITTED
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Tasks - Long Text",
    group = "UpcomingTasksCard - States",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun UpcomingTasksCardLongTextPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "Wednesday, 15 March 2024",
                tasks = listOf(
                    UpcomingTask(
                        id = "task_long",
                        dueTime = "11.59pm",
                        title = "FIT3155: Advanced Algorithms and Data Structures - Assignment 2: Graph Theory and Dynamic Programming",
                        subtitle = "Not submitted - Due in 2 hours",
                        status = TaskStatus.NOT_SUBMITTED
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Tasks - Accessibility (Large Font)",
    group = "UpcomingTasksCard",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun UpcomingTasksCardAccessibilityPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "Sun, 12 March",
                tasks = listOf(
                    UpcomingTask(
                        id = "task_access",
                        dueTime = "5pm",
                        title = "FIT2050: In-class quizzes",
                        subtitle = "Not submitted",
                        status = TaskStatus.NOT_SUBMITTED
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Tasks - Empty List",
    group = "UpcomingTasksCard - States",
    showBackground = true
)
@Composable
private fun UpcomingTasksCardEmptyPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            UpcomingTasksCard(
                label = "Tue, 14 March",
                tasks = emptyList()
            )
        }
    }
}
