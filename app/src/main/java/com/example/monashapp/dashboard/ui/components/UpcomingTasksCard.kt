package com.example.monashapp.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.monashapp.core.model.dashboard.TaskStatus
import com.example.monashapp.core.model.dashboard.UpcomingTask
import com.example.monashapp.dashboard.ui.DashboardColors
import com.example.monashapp.dashboard.ui.DashboardSpacing

@Composable
fun UpcomingTasksCard(label: String, tasks: List<UpcomingTask>) {
    Column(verticalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF1D1B20)
        )
        tasks.forEach { task ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.itemSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(DashboardColors.taskBadge)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.dueTime,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = task.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusColor(task.status)
                    )
                }
            }
        }
    }
}

private fun statusColor(status: TaskStatus): Color = when (status) {
    TaskStatus.NOT_SUBMITTED -> Color(0xFF49454F)
    TaskStatus.SUBMITTED -> Color(0xFF49454F)
}

