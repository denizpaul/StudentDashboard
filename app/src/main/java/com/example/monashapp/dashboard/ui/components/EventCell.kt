package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

/**
 * Event icon types
 */
sealed class EventIcon {
    data class DurationLine(val color: Color) : EventIcon()
    data class TaskCircle(val color: Color) : EventIcon()
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
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun EventCell(
    icon: EventIcon,
    time: EventTime,
    title: String,
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        when (icon) {
            is EventIcon.DurationLine -> {
                Spacer(
                    modifier = Modifier
                        .width(DashboardSpacing.indicatorWidth)
                        .height(DashboardSpacing.indicatorHeight)
                        .padding(top = 4.dp)
                        .clip(CircleShape)
                        .background(icon.color)
                )
            }
            is EventIcon.TaskCircle -> {
                Spacer(
                    modifier = Modifier
                        .size(DashboardSpacing.indicatorCircle)
                        .padding(top = 3.dp)
                        .clip(CircleShape)
                        .background(icon.color)
                )
            }
        }

        Spacer(modifier = Modifier.width(DashboardSpacing.indicatorGap))

        Column(modifier = Modifier.weight(1f)) {
            when (time) {
                is EventTime.Single -> {
                    Text(
                        text = time.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                is EventTime.Range -> {
                    Column {
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
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(2.2f)) {
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
                time = EventTime.Range("10.30am", "1.30pm"),
                title = "FIT2001: Tutorial",
                subtitle = "S4, 13 College Walk, Clayton"
            )
        }
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.TaskCircle(dashboardColors.taskBadge),
                time = EventTime.Single("5pm"),
                title = "FIT2050: In-class quizzes submission closes",
                subtitle = "Not submitted",
                subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.TaskCircle(dashboardColors.sessionAssignmentIndicator),
                time = EventTime.Single("5pm"),
                title = "MTK1000: Weekly quizzes",
                subtitle = "Submitted",
                subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
                time = EventTime.Range("10.30am", "1.30pm"),
                title = "FIT2001: Tutorial",
                subtitle = "S4, 13 College Walk, Clayton"
            )
        }
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.TaskCircle(dashboardColors.taskBadge),
                time = EventTime.Single("5pm"),
                title = "FIT2050: In-class quizzes submission closes for all students",
                subtitle = "Building 123, Very Long Street Name, Clayton Campus"
            )
        }
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
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            EventCell(
                icon = EventIcon.DurationLine(dashboardColors.sessionClassIndicator),
                time = EventTime.Range("10.30am", "1.30pm"),
                title = "FIT2001: Tutorial",
                subtitle = "S4, 13 College Walk, Clayton"
            )
        }
    }
}

