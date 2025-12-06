package com.example.monashapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material Design 3 Shape System
 *
 * M3 defines 5 shape categories:
 * - ExtraSmall (4dp) - For small chips, buttons
 * - Small (8dp) - For small components
 * - Medium (12dp) - For cards, dialogs
 * - Large (16dp) - For large cards, sheets
 * - ExtraLarge (28dp) - For extra large surfaces
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

