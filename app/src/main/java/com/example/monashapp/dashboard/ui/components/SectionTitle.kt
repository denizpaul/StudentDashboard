package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme
import com.example.monashapp.ui.theme.dashboardColors

/**
 * SectionTitle - Secondary section label
 *
 * Purpose: Display lighter/smaller section headers for non-date groupings
 *
 * @param title The section title text
 * @param showDivider Whether to show a divider above the title
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun SectionTitle(
    title: String,
    showDivider: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Optional divider
        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .height(DashboardSpacing.dividerThickness)
                    .fillMaxWidth()
                    .background(MaterialTheme.dashboardColors.divider)
            )
            Spacer(modifier = Modifier.height(DashboardSpacing.indicatorGap)) // 16dp spacing after divider
        }

        // Section title
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), // Bold 14sp, 20px line
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Figma: #49454F
            modifier = Modifier.padding(
                start = DashboardSpacing.smallSpacing, // 8dp left padding per Figma
                top = if (showDivider) 0.dp else DashboardSpacing.indicatorGap // 16dp top if no divider
            )
        )
    }
}

// Preview parameter provider
private class SectionTitleProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Available parking spots",
        "Upcoming tasks",
        "This week",
        "Next month"
    )
}

@Preview(
    name = "SectionTitle - No Divider",
    group = "SectionTitle",
    showBackground = true
)
@Preview(
    name = "SectionTitle - No Divider Dark",
    group = "SectionTitle",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun SectionTitlePreview(
    @PreviewParameter(SectionTitleProvider::class) title: String
) {
    MonashTheme {
        Surface {
            SectionTitle(title = title, showDivider = false)
        }
    }
}

@Preview(
    name = "SectionTitle - With Divider",
    group = "SectionTitle - Variants",
    showBackground = true
)
@Composable
private fun SectionTitleWithDividerPreview() {
    MonashTheme {
        Surface {
            SectionTitle(
                title = "Available parking spots",
                showDivider = true
            )
        }
    }
}

@Preview(
    name = "SectionTitle - Long Text",
    group = "SectionTitle - Variants",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun SectionTitleLongTextPreview() {
    MonashTheme {
        Surface {
            SectionTitle(
                title = "Available parking spots on campus",
                showDivider = false
            )
        }
    }
}

@Preview(
    name = "SectionTitle - Accessibility",
    group = "SectionTitle - Variants",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun SectionTitleAccessibilityPreview() {
    MonashTheme {
        Surface {
            SectionTitle(
                title = "Available parking spots",
                showDivider = true
            )
        }
    }
}

