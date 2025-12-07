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
import com.example.monashapp.core.model.dashboard.TaskStatus
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
        horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.indicatorGap) // Figma: 16dp gap
    ) {
        // Icon Column
        when (icon) {
            is EventIcon.DurationLine -> {
                // Vertical line for class/duration events
                Column(
                    modifier = Modifier
                        .width(DashboardSpacing.indicatorWidth) // Figma: 6dp
                        .height(DashboardSpacing.indicatorHeight) // Figma: 48dp
                        .padding(top = 4.dp) // Align with text baseline
                        .clip(CircleShape)
                        .background(icon.color),
                    verticalArrangement = Arrangement.Center
                ) {}
            }
            is EventIcon.TaskCircle -> {
                // Circular icon for tasks/assignments
                Column(
                    modifier = Modifier
                        .width(DashboardSpacing.indicatorCircle) // Figma: 32dp
                        .padding(top = 4.dp), // Align with text baseline
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(DashboardSpacing.indicatorCircle) // Figma: 32dp
                            .clip(CircleShape)
                            .background(icon.color)
                    )
                }
            }
        }

        // Content Column
        Column(modifier = Modifier.weight(1f)) {
            // Time display
            when (time) {
                is EventTime.Single -> {
                    Text(
                        text = time.time,
                        style = MaterialTheme.typography.bodyLarge, // Bold 16sp, 24px line
                        color = MaterialTheme.colorScheme.onSurface // Figma: #1D1B20
                    )
                }
                is EventTime.Range -> {
                    Row(horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.smallSpacing)) {
                        Text(
                            text = time.startTime,
                            style = MaterialTheme.typography.bodyLarge, // Bold 16sp, 24px line
                            color = MaterialTheme.colorScheme.onSurface // Figma: #1D1B20
                        )
                        Text(
                            text = "– ${time.endTime}",
                            style = MaterialTheme.typography.bodySmall, // Medium 12sp, 16px line
                            color = MaterialTheme.colorScheme.onSurfaceVariant // Figma: #49454F
                        )
                    }
                }
            }

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium, // Medium 16sp, 24px line
                color = MaterialTheme.colorScheme.onSurface // Figma: #1D1B20
            )

            // Subtitle (optional)
            subtitle?.let {
                Column(modifier = Modifier.padding(top = DashboardSpacing.tinySpacing)) { // 2dp padding
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium, // Regular 14sp, 20px line
                        color = subtitleColor
                    )
                }
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

