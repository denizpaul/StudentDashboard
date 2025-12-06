package com.example.monashapp.dashboard.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Dashboard Design Tokens
 *
 * Spacing values follow M3 principles (multiples of 4dp or 8dp)
 * Colors are now accessed via MaterialTheme.dashboardColors extension
 * which adapts to light/dark themes automatically
 */
object DashboardSpacing {
    val screenHorizontal: Dp = 24.dp
    val screenVertical: Dp = 32.dp
    val sectionSpacing: Dp = 24.dp
    val cardPadding: Dp = 20.dp
    val cardCorner: Dp = 28.dp
    val itemSpacing: Dp = 16.dp
    val smallSpacing: Dp = 8.dp
    val dividerThickness: Dp = 1.dp
}

/**
 * DEPRECATED: Use MaterialTheme.dashboardColors instead
 *
 * Dashboard colors are now theme-aware and can be accessed via:
 * @sample
 * val dashboardColors = MaterialTheme.dashboardColors
 * Box(modifier = Modifier.background(dashboardColors.sessionClassIndicator))
 *
 * Available colors:
 * - sessionClassIndicator
 * - sessionAssignmentIndicator
 * - taskBadge
 * - parkingBlue
 * - parkingRed
 * - divider
 */
@Deprecated(
    message = "Use MaterialTheme.dashboardColors instead for theme-aware colors",
    replaceWith = ReplaceWith("MaterialTheme.dashboardColors", "com.example.monashapp.ui.theme.dashboardColors"),
    level = DeprecationLevel.WARNING
)
object DashboardColors

