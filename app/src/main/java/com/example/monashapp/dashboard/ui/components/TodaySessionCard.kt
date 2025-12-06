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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.dashboard.ui.DashboardColors
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme

@Composable
fun TodaySessionCard(session: TodaySession) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)
    ) {
        if (session.category == SessionCategory.CLASS) {
            Column(
                modifier = Modifier
                    .width(12.dp)
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(DashboardColors.sessionClassIndicator.copy(alpha = 0.8f)),
                verticalArrangement = Arrangement.Center
            ) {}
        } else {
            Column(
                modifier = Modifier.width(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(DashboardColors.sessionAssignmentIndicator)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.smallSpacing)) {
                Text(
                    text = session.startTime,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1D1B20)
                )
                session.endTime?.let {
                    Text(text = "– $it", style = MaterialTheme.typography.labelSmall, color = Color(0xFF49454F))
                }
            }
            Text(text = session.title, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1D1B20))
            Text(text = session.subtitle, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF49454F))
        }
    }
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
