package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

/**
 * TodaySessionCard - Refactored to use EventCell component
 *
 * This component adapts TodaySession data model to the EventCell component
 */
@Composable
fun TodaySessionCard(session: TodaySession, modifier: Modifier = Modifier) {
    val dashboardColors = MaterialTheme.dashboardColors

    // Convert session category to EventIcon
    val icon = when (session.category) {
        SessionCategory.CLASS -> EventIcon.DurationLine(dashboardColors.sessionClassIndicator)
        SessionCategory.ASSIGNMENT -> EventIcon.TaskCircle(dashboardColors.sessionAssignmentIndicator)
    }

    // Convert time to EventTime
    val time = if (session.endTime != null) {
        EventTime.Range(session.startTime, session.endTime)
    } else {
        EventTime.Single(session.startTime)
    }

    // Use EventCell component
    EventCell(
        icon = icon,
        time = time,
        title = session.title,
        subtitle = session.subtitle,
        subtitleColor = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

// Preview parameter provider for different session scenarios
private class SessionProvider : PreviewParameterProvider<TodaySession> {
    override val values = sequenceOf(
        TodaySession(
            id = "class_1",
            startTime = "10.30am",
            endTime = "1.30pm",
            title = "FIT2001: Tutorial",
            subtitle = "S4, 13 College Walk, Clayton",
            category = SessionCategory.CLASS
        ),
        TodaySession(
            id = "assignment_1",
            startTime = "5pm",
            endTime = null,
            title = "MTK1000: Weekly quizzes",
            subtitle = "Submitted",
            category = SessionCategory.ASSIGNMENT
        ),
        TodaySession(
            id = "class_long",
            startTime = "2.15pm",
            endTime = "5.45pm",
            title = "FIT3155: Advanced Algorithms and Data Structures",
            subtitle = "Building 123, Room 456, Very Long Campus Name Street, Clayton Campus",
            category = SessionCategory.CLASS
        )
    )
}

@Preview(
    name = "Session Card - Light",
    group = "TodaySessionCard",
    showBackground = true
)
@Preview(
    name = "Session Card - Dark",
    group = "TodaySessionCard",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun TodaySessionCardPreview(
    @PreviewParameter(SessionProvider::class) session: TodaySession
) {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            TodaySessionCard(session = session)
        }
    }
}

@Preview(
    name = "Session - CLASS Category",
    group = "TodaySessionCard - Categories",
    showBackground = true
)
@Composable
private fun TodaySessionCardClassPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            TodaySessionCard(
                session = TodaySession(
                    id = "class",
                    startTime = "10.30am",
                    endTime = "1.30pm",
                    title = "FIT2001: Tutorial",
                    subtitle = "S4, 13 College Walk, Clayton",
                    category = SessionCategory.CLASS
                )
            )
        }
    }
}

@Preview(
    name = "Session - ASSIGNMENT Category",
    group = "TodaySessionCard - Categories",
    showBackground = true
)
@Composable
private fun TodaySessionCardAssignmentPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            TodaySessionCard(
                session = TodaySession(
                    id = "assignment",
                    startTime = "5pm",
                    endTime = null,
                    title = "MTK1000: Weekly quizzes",
                    subtitle = "Submitted",
                    category = SessionCategory.ASSIGNMENT
                )
            )
        }
    }
}

@Preview(
    name = "Session - Accessibility (Large Font)",
    group = "TodaySessionCard",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun TodaySessionCardAccessibilityPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            TodaySessionCard(
                session = TodaySession(
                    id = "class_access",
                    startTime = "10.30am",
                    endTime = "1.30pm",
                    title = "FIT2001: Tutorial",
                    subtitle = "S4, 13 College Walk, Clayton",
                    category = SessionCategory.CLASS
                )
            )
        }
    }
}

@Preview(
    name = "Session - Small Screen",
    group = "TodaySessionCard",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun TodaySessionCardSmallScreenPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            TodaySessionCard(
                session = TodaySession(
                    id = "class_small",
                    startTime = "10.30am",
                    endTime = "1.30pm",
                    title = "FIT2001: Advanced Tutorial",
                    subtitle = "S4, 13 College Walk, Clayton",
                    category = SessionCategory.CLASS
                )
            )
        }
    }
}
