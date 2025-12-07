package com.example.monashapp.dashboard.ui

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Dashboard Design Tokens - Exact Figma specifications
 *
 * All spacing values match the Figma design pixel-perfectly
 * Colors are now accessed via MaterialTheme.dashboardColors extension
 * which adapts to light/dark themes automatically
 */
object DashboardSpacing {
    // Screen padding - Figma: 24dp horizontal, 48dp top, 40dp bottom
    val screenHorizontal: Dp = 24.dp // Figma exact
    val screenTop: Dp = 48.dp // Figma: 48dp from top
    val screenBottom: Dp = 40.dp // Figma: 40dp from bottom

    // Section and card spacing - Figma exact
    val sectionSpacing: Dp = 24.dp // Figma: 24dp gap between sections
    val cardSpacing: Dp = 16.dp // Figma: 16dp gap between cards
    val cardPadding: Dp = 20.dp // Figma: 20dp all sides
    val cardCorner: Dp = 28.dp // Figma: 28dp rounded corners

    // Item spacing within cards - Figma exact
    val itemSpacing: Dp = 24.dp // Figma: 24dp gap between items in card
    val indicatorGap: Dp = 16.dp // Figma: 16dp gap between indicator and content
    val smallSpacing: Dp = 8.dp // For minor adjustments
    val tinySpacing: Dp = 2.dp // Figma: 2dp gap between title and subtitle

    // Indicator dimensions - Figma exact
    val indicatorWidth: Dp = 6.dp // Figma: 6dp width for class indicator
    val indicatorHeight: Dp = 48.dp // Figma: 48dp height for class indicator
    val indicatorCircle: Dp = 32.dp // Figma: 32dp circle for assignment/task

    // Other elements
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

