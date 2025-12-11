package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

/**
 * Data point for SmallCell component
 *
 * @param label Single letter label (e.g., "B" for Blue, "R" for Red)
 * @param value Numeric value to display
 * @param color Background color for the badge
 */
data class DataPoint(
    val label: String,
    val value: Int,
    val color: Color
)

/**
 * SmallCell - Compact info with multiple data points
 *
 * Purpose: Display a title with multiple color-coded data points (badges with values)
 *
 * @param title The title text displayed on the left
 * @param dataPoints List of data points to display on the right
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun SmallCell(
    title: String,
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = DashboardSpacing.smallSpacing)
            .semantics(mergeDescendants = true) {
                // Merge all children into one announcement for screen readers
                contentDescription = buildString {
                    append(title)
                    if (dataPoints.isNotEmpty()) {
                        append(", ")
                        dataPoints.forEachIndexed { index, dataPoint ->
                            // Expand single-letter labels to full words for clarity
                            val colorName = when (dataPoint.label) {
                                "B" -> "Blue"
                                "R" -> "Red"
                                "G" -> "Green"
                                else -> dataPoint.label
                            }
                            append("$colorName ${dataPoint.value}")
                            if (index < dataPoints.lastIndex) append(", ")
                        }
                        append(" spots available")
                    }
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title on left
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium, // Medium 16sp, 24px line
            color = MaterialTheme.colorScheme.onSurface, // Figma: #1D1B20
            modifier = Modifier.weight(1f, fill = false) // Allow text to wrap on large fonts
        )

        // Data points on right
        Row(
            horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.smallSpacing), // 8dp gap between badge groups
            verticalAlignment = Alignment.CenterVertically
        ) {
            dataPoints.forEach { dataPoint ->
                DataPointBadge(dataPoint = dataPoint)
            }
        }
    }
}

/**
 * Individual data point badge
 */
@Composable
private fun DataPointBadge(dataPoint: DataPoint) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp), // Gap between badge and number
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular badge with label - Figma: 24dp circle
        Text(
            text = dataPoint.label,
            color = Color.White, // White text on colored background
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            modifier = Modifier
                .background(dataPoint.color, CircleShape)
                .padding(
                    horizontal = 8.dp, // Left/right padding for centering
                    vertical = 4.dp // Top/bottom padding to achieve 24dp height
                )
        )

        // Value
        Text(
            text = dataPoint.value.toString(),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold), // SemiBold 16sp, 24px line height
            color = MaterialTheme.colorScheme.onSurface // Figma: #1D1B20
        )
    }
}

// Preview Composables
@Preview(
    name = "SmallCell - Parking",
    group = "SmallCell",
    showBackground = true
)
@Composable
private fun SmallCellPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "North (multi-level)",
                dataPoints = listOf(
                    DataPoint("B", 12, dashboardColors.parkingBlue),
                    DataPoint("R", 5, dashboardColors.parkingRed)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Zero Values",
    group = "SmallCell",
    showBackground = true
)
@Composable
private fun SmallCellZeroPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "West 1",
                dataPoints = listOf(
                    DataPoint("B", 0, dashboardColors.parkingBlue),
                    DataPoint("R", 2, dashboardColors.parkingRed)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - High Numbers",
    group = "SmallCell - Variants",
    showBackground = true
)
@Composable
private fun SmallCellHighNumbersPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "Campus Center",
                dataPoints = listOf(
                    DataPoint("B", 150, dashboardColors.parkingBlue),
                    DataPoint("R", 89, dashboardColors.parkingRed)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Dark Mode",
    group = "SmallCell",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun SmallCellDarkPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "North (multi-level)",
                dataPoints = listOf(
                    DataPoint("B", 12, dashboardColors.parkingBlue),
                    DataPoint("R", 5, dashboardColors.parkingRed)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Long Title",
    group = "SmallCell - Variants",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun SmallCellLongTitlePreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "South Parking Structure",
                dataPoints = listOf(
                    DataPoint("B", 12, dashboardColors.parkingBlue),
                    DataPoint("R", 5, dashboardColors.parkingRed)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Single DataPoint",
    group = "SmallCell - Variants",
    showBackground = true
)
@Composable
private fun SmallCellSingleDataPointPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "Visitor Parking",
                dataPoints = listOf(
                    DataPoint("B", 8, dashboardColors.parkingBlue)
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Three DataPoints",
    group = "SmallCell - Variants",
    showBackground = true
)
@Composable
private fun SmallCellThreeDataPointsPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "Main Campus",
                dataPoints = listOf(
                    DataPoint("B", 12, dashboardColors.parkingBlue),
                    DataPoint("R", 5, dashboardColors.parkingRed),
                    DataPoint("G", 8, Color(0xFF34A853)) // Green for example
                )
            )
        }
    }
}

@Preview(
    name = "SmallCell - Accessibility",
    group = "SmallCell - Variants",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun SmallCellAccessibilityPreview() {
    MonashTheme {
        Surface {
            val dashboardColors = MaterialTheme.dashboardColors
            SmallCell(
                title = "North (multi-level)",
                dataPoints = listOf(
                    DataPoint("B", 12, dashboardColors.parkingBlue),
                    DataPoint("R", 5, dashboardColors.parkingRed)
                )
            )
        }
    }
}

