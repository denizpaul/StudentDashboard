package com.example.monashapp.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Custom multi-preview annotation for testing light and dark modes together.
 * Apply this annotation to a Composable to automatically generate both light and dark previews.
 */
@Preview(
    name = "Light Mode",
    group = "Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    group = "Theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class PreviewLightDark

/**
 * Custom multi-preview annotation for testing different font scales.
 * Use this to ensure components handle accessibility text scaling properly.
 */
@Preview(
    name = "Normal Font",
    group = "Font Scale",
    showBackground = true,
    fontScale = 1.0f
)
@Preview(
    name = "Large Font",
    group = "Font Scale",
    showBackground = true,
    fontScale = 1.5f
)
@Preview(
    name = "Extra Large Font",
    group = "Font Scale",
    showBackground = true,
    fontScale = 2.0f
)
annotation class PreviewFontScales

/**
 * Custom multi-preview annotation for testing different screen sizes.
 * Useful for ensuring responsive layouts work across devices.
 */
@Preview(
    name = "Phone",
    group = "Screen Size",
    showBackground = true,
    device = "id:pixel_5"
)
@Preview(
    name = "Small Phone",
    group = "Screen Size",
    showBackground = true,
    widthDp = 320,
    heightDp = 640
)
@Preview(
    name = "Tablet",
    group = "Screen Size",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
annotation class PreviewScreenSizes

/**
 * Comprehensive multi-preview annotation combining light/dark modes with font scales.
 * Use this for critical components that need thorough testing.
 */
@Preview(
    name = "Light - Normal Font",
    group = "Comprehensive",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    fontScale = 1.0f
)
@Preview(
    name = "Dark - Normal Font",
    group = "Comprehensive",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 1.0f
)
@Preview(
    name = "Light - Large Font",
    group = "Comprehensive",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    fontScale = 1.5f
)
@Preview(
    name = "Dark - Large Font",
    group = "Comprehensive",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 1.5f
)
annotation class PreviewComplete

/**
 * Preview annotation for landscape orientation testing.
 */
@Preview(
    name = "Landscape",
    group = "Orientation",
    showBackground = true,
    widthDp = 640,
    heightDp = 360
)
annotation class PreviewLandscape

