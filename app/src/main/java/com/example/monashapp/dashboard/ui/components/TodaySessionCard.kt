package com.example.monashapp.dashboard.ui.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import com.example.monashapp.core.model.dashboard.SessionCategory
import com.example.monashapp.core.model.dashboard.TodaySession
import com.example.monashapp.dashboard.ui.DashboardColors
import com.example.monashapp.dashboard.ui.DashboardSpacing

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