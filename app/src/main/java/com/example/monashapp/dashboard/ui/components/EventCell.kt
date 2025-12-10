package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

sealed class EventIcon {
    data class DurationLine(val color: Color) : EventIcon()
    data class TaskCircle(
        val color: Color,
        @DrawableRes val iconRes: Int? = null
    ) : EventIcon()
}

/**
 * Event time display options
 */
sealed class EventTime {
    data class Single(val time: String) : EventTime()
    data class Range(val startTime: String, val endTime: String) : EventTime()
}

/**
 * EventCell - Display scheduled events
 *
 * Purpose: Flexible component for displaying events with different icon types and time formats
 *
 * @param icon Event icon - either a vertical line for durations or circular icon for tasks
 * @param time Event time - either single time or time range
 * @param title Event title
 * @param subtitle Optional subtitle/location/status
 * @param subtitleColor Color for subtitle text (e.g., status-based)
 * @param iconDescription Accessibility description for the icon type (e.g., "Class session", "Task")
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconDescription: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                // Merge all child semantics into one announcement for screen readers
                contentDescription = buildString {
                    // Include icon meaning if provided
                    iconDescription?.let { append("$it, ") }

                    // Include time information
                    when (time) {
                        is EventTime.Single -> append("${time.time}, ")
                        is EventTime.Range -> append("${time.startTime} to ${time.endTime}, ")
                    }

                    // Include title
                    append(title)

                    // Include subtitle/location if present
                    subtitle?.let { append(", $it") }
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        // Icon section - fixed width for consistent alignment
        Box(
            modifier = Modifier.width(DashboardSpacing.indicatorCircle),
            contentAlignment = Alignment.Center
        ) {
            when (icon) {
                is EventIcon.DurationLine -> {
                    Spacer(
                        modifier = Modifier
                            .width(DashboardSpacing.indicatorWidth)
                            .height(DashboardSpacing.indicatorHeight)
                            .clip(CircleShape)
                            .background(icon.color)
                    )
                }
                is EventIcon.TaskCircle -> {
                    Box(
                        modifier = Modifier.size(DashboardSpacing.indicatorCircle),
                        contentAlignment = Alignment.Center
                    ) {
                        Spacer(
                            modifier = Modifier
                                .size(DashboardSpacing.indicatorCircle)
                                .clip(CircleShape)
                                .background(icon.color)
                        )

                        icon.iconRes?.let { iconRes ->
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = null, // Parent Row handles description via semantics
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(DashboardSpacing.indicatorGap))

        // Time section - fixed width for consistent alignment
        Column(
            modifier = Modifier.width(52.dp)
        ) {
            when (time) {
                is EventTime.Single -> {
                    Text(
                        text = time.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                is EventTime.Range -> {
                    Text(
                        text = time.startTime,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "-${time.endTime}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Content section - takes remaining space
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = subtitleColor,
                    modifier = Modifier.padding(top = DashboardSpacing.tinySpacing)
                )
            }
        }
    }
}

// Preview Composables
@Preview(
    name = "EventCell - Class with Range",
    group = "EventCell",
    showBackground = true
)
@Composable
private fun EventCellClassPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
            time = EventTime.Range("10.30am", "1.30pm"),
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton"
        )
    }
}

@Preview(
    name = "EventCell - Task Single Time",
    group = "EventCell",
    showBackground = true
)
@Composable
private fun EventCellTaskPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.TaskCircle(
                color = dashboardColors.taskBadge,
                iconRes = R.drawable.ic_task
            ),
            time = EventTime.Single("5pm"),
            title = "FIT2050: In-class quizzes submission closes",
            subtitle = "Not submitted",
            subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "EventCell - Assignment",
    group = "EventCell",
    showBackground = true
)
@Composable
private fun EventCellAssignmentPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.TaskCircle(
                color = dashboardColors.sessionAssignmentIndicator,
                iconRes = R.drawable.ic_task
            ),
            time = EventTime.Single("5pm"),
            title = "MTK1000: Weekly quizzes",
            subtitle = "Submitted",
            subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "EventCell - Dark Mode",
    group = "EventCell",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun EventCellDarkPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
            time = EventTime.Range("10.30am", "1.30pm"),
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton"
        )
    }
}

@Preview(
    name = "EventCell - Long Text",
    group = "EventCell - Edge Cases",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun EventCellLongTextPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.TaskCircle(
                color = dashboardColors.taskBadge,
                iconRes = R.drawable.ic_task
            ),
            time = EventTime.Single("5pm"),
            title = "FIT2050: In-class quizzes submission closes for all students",
            subtitle = "Building 123, Very Long Street Name, Clayton Campus"
        )
    }
}

@Preview(
    name = "EventCell - Accessibility",
    group = "EventCell - Edge Cases",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun EventCellAccessibilityPreview() {
    MonashTheme {
        val dashboardColors = MaterialTheme.dashboardColors
        EventCell(
            icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
            time = EventTime.Range("10.30am", "1.30pm"),
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton"
        )
    }
}

