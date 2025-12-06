package com.example.monashapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

/**
 * Complete M3 Light Color Scheme with all semantic color roles
 */
private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,

    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,

    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,

    error = md_theme_light_error,
    onError = md_theme_light_onError,
    errorContainer = md_theme_light_errorContainer,
    onErrorContainer = md_theme_light_onErrorContainer,

    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,

    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,

    outline = md_theme_light_outline,
    outlineVariant = md_theme_light_outlineVariant,

    inverseSurface = md_theme_light_inverseSurface,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inversePrimary = md_theme_light_inversePrimary,

    scrim = md_theme_light_scrim,
    surfaceTint = md_theme_light_surfaceTint
)

/**
 * Complete M3 Dark Color Scheme with all semantic color roles
 */
private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,

    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,

    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,

    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    errorContainer = md_theme_dark_errorContainer,
    onErrorContainer = md_theme_dark_onErrorContainer,

    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,

    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,

    outline = md_theme_dark_outline,
    outlineVariant = md_theme_dark_outlineVariant,

    inverseSurface = md_theme_dark_inverseSurface,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inversePrimary = md_theme_dark_inversePrimary,

    scrim = md_theme_dark_scrim,
    surfaceTint = md_theme_dark_surfaceTint
)

/**
 * Dashboard-specific colors accessible via composition local
 * These adapt to light/dark themes automatically
 */
data class DashboardColors(
    val sessionClassIndicator: androidx.compose.ui.graphics.Color,
    val sessionAssignmentIndicator: androidx.compose.ui.graphics.Color,
    val taskBadge: androidx.compose.ui.graphics.Color,
    val parkingBlue: androidx.compose.ui.graphics.Color,
    val parkingRed: androidx.compose.ui.graphics.Color,
    val divider: androidx.compose.ui.graphics.Color
)

private val LocalDashboardColors = staticCompositionLocalOf {
    DashboardColors(
        sessionClassIndicator = md_theme_light_sessionClassIndicator,
        sessionAssignmentIndicator = md_theme_light_sessionAssignmentIndicator,
        taskBadge = md_theme_light_taskBadge,
        parkingBlue = md_theme_light_parkingBlue,
        parkingRed = md_theme_light_parkingRed,
        divider = md_theme_light_divider
    )
}

/**
 * Access dashboard-specific colors from any composable
 * Usage: val dashboardColors = MaterialTheme.dashboardColors
 */
val MaterialTheme.dashboardColors: DashboardColors
    @Composable
    get() = LocalDashboardColors.current

/**
 * MonashTheme - Main theme composable with M3 support
 *
 * Features:
 * - Dynamic color support for Android 12+ (Material You)
 * - Complete M3 color scheme with all semantic roles
 * - Dashboard-specific colors that adapt to theme
 * - Automatic light/dark theme switching
 *
 * @param darkTheme Whether to use dark theme (defaults to system preference)
 * @param dynamicColor Whether to use dynamic color from system (Android 12+)
 * @param content The composable content to theme
 */
@Composable
fun MonashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Determine color scheme based on dynamic color availability and theme
    val colorScheme = when {
        // Dynamic color is available on Android 12+ (API 31+)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        // Fall back to custom color scheme for older devices
        darkTheme -> DarkColors
        else -> LightColors
    }

    // Dashboard-specific colors that adapt to theme
    val dashboardColors = if (darkTheme) {
        DashboardColors(
            sessionClassIndicator = md_theme_dark_sessionClassIndicator,
            sessionAssignmentIndicator = md_theme_dark_sessionAssignmentIndicator,
            taskBadge = md_theme_dark_taskBadge,
            parkingBlue = md_theme_dark_parkingBlue,
            parkingRed = md_theme_dark_parkingRed,
            divider = md_theme_dark_divider
        )
    } else {
        DashboardColors(
            sessionClassIndicator = md_theme_light_sessionClassIndicator,
            sessionAssignmentIndicator = md_theme_light_sessionAssignmentIndicator,
            taskBadge = md_theme_light_taskBadge,
            parkingBlue = md_theme_light_parkingBlue,
            parkingRed = md_theme_light_parkingRed,
            divider = md_theme_light_divider
        )
    }

    // Provide dashboard colors via composition local
    CompositionLocalProvider(LocalDashboardColors provides dashboardColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}


