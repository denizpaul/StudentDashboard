package com.example.monashapp.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Material Design 3 Color Palette
 *
 * These are the base colors that will be used to construct the light and dark color schemes.
 * They follow M3 color system principles and adapt to light/dark themes.
 */

// Primary colors - Main brand color
val md_theme_light_primary = Color(0xFF6750A4)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFEADDFF)
val md_theme_light_onPrimaryContainer = Color(0xFF21005D)

val md_theme_dark_primary = Color(0xFFD0BCFF)
val md_theme_dark_onPrimary = Color(0xFF381E72)
val md_theme_dark_primaryContainer = Color(0xFF4F378B)
val md_theme_dark_onPrimaryContainer = Color(0xFFEADDFF)

// Secondary colors - Supporting color
val md_theme_light_secondary = Color(0xFF625B71)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE8DEF8)
val md_theme_light_onSecondaryContainer = Color(0xFF1D192B)

val md_theme_dark_secondary = Color(0xFFCCC2DC)
val md_theme_dark_onSecondary = Color(0xFF332D41)
val md_theme_dark_secondaryContainer = Color(0xFF4A4458)
val md_theme_dark_onSecondaryContainer = Color(0xFFE8DEF8)

// Tertiary colors - Accent color
val md_theme_light_tertiary = Color(0xFF7D5260)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD8E4)
val md_theme_light_onTertiaryContainer = Color(0xFF31111D)

val md_theme_dark_tertiary = Color(0xFFEFB8C8)
val md_theme_dark_onTertiary = Color(0xFF492532)
val md_theme_dark_tertiaryContainer = Color(0xFF633B48)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD8E4)

// Error colors - For error states
val md_theme_light_error = Color(0xFFB3261E)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFF9DEDC)
val md_theme_light_onErrorContainer = Color(0xFF410E0B)

val md_theme_dark_error = Color(0xFFF2B8B5)
val md_theme_dark_onError = Color(0xFF601410)
val md_theme_dark_errorContainer = Color(0xFF8C1D18)
val md_theme_dark_onErrorContainer = Color(0xFFF9DEDC)

// Background colors
val md_theme_light_background = Color(0xFFFFFBFE)
val md_theme_light_onBackground = Color(0xFF1C1B1F)

val md_theme_dark_background = Color(0xFF1C1B1F)
val md_theme_dark_onBackground = Color(0xFFE6E1E5)

// Surface colors - For cards and other surfaces
val md_theme_light_surface = Color(0xFFFFFBFE)
val md_theme_light_onSurface = Color(0xFF1C1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE7E0EC)
val md_theme_light_onSurfaceVariant = Color(0xFF49454F)

val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSurface = Color(0xFFE6E1E5)
val md_theme_dark_surfaceVariant = Color(0xFF49454F)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4D0)

// Outline colors - For borders and dividers
val md_theme_light_outline = Color(0xFF79747E)
val md_theme_light_outlineVariant = Color(0xFFCAC4D0)

val md_theme_dark_outline = Color(0xFF938F99)
val md_theme_dark_outlineVariant = Color(0xFF49454F)

// Inverse colors - For elements on primary-colored backgrounds
val md_theme_light_inverseSurface = Color(0xFF313033)
val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
val md_theme_light_inversePrimary = Color(0xFFD0BCFF)

val md_theme_dark_inverseSurface = Color(0xFFE6E1E5)
val md_theme_dark_inverseOnSurface = Color(0xFF313033)
val md_theme_dark_inversePrimary = Color(0xFF6750A4)

// Scrim - For modal overlays
val md_theme_light_scrim = Color(0xFF000000)
val md_theme_dark_scrim = Color(0xFF000000)

// Surface tint - Used to tint elevated surfaces
val md_theme_light_surfaceTint = Color(0xFF6750A4)
val md_theme_dark_surfaceTint = Color(0xFFD0BCFF)

/**
 * Dashboard-specific semantic colors
 * These are mapped to appropriate M3 color roles but maintain semantic naming
 * for dashboard features. They adapt to light/dark themes.
 */

// Session indicators - Using tertiary and secondary tones for variety
val md_theme_light_sessionClassIndicator = Color(0xFFEF9A9A) // Soft red for classes
val md_theme_light_sessionAssignmentIndicator = Color(0xFFFFCC80) // Soft orange for assignments

val md_theme_dark_sessionClassIndicator = Color(0xFFE57373) // Brighter red for dark mode
val md_theme_dark_sessionAssignmentIndicator = Color(0xFFFFB74D) // Brighter orange for dark mode

// Task badge - Using tertiary container for task items
val md_theme_light_taskBadge = Color(0xFFCE93D8) // Purple from tertiary palette
val md_theme_dark_taskBadge = Color(0xFFBA68C8) // Brighter purple for dark mode

// Parking permit colors - Semantic colors for parking zones
val md_theme_light_parkingBlue = Color(0xFF42A5F5) // Blue permit
val md_theme_light_parkingRed = Color(0xFFEF5350) // Red permit

val md_theme_dark_parkingBlue = Color(0xFF64B5F6) // Brighter blue for dark mode
val md_theme_dark_parkingRed = Color(0xFFE57373) // Brighter red for dark mode

// Divider color - Using outline variant with transparency
val md_theme_light_divider = Color(0x1F000000) // 12% black
val md_theme_dark_divider = Color(0x1FFFFFFF) // 12% white

