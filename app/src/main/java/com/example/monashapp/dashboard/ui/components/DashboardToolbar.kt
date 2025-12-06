package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.monashapp.ui.theme.MonashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardToolbar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// Preview parameter provider for different greeting scenarios
private class ToolbarTitleProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Hey, Kier",
        "Good morning, Alex",
        "Welcome back, Jennifer",
        "Hi, A" // Short name
    )
}

@Preview(
    name = "Toolbar - Light",
    group = "DashboardToolbar",
    showBackground = true
)
@Preview(
    name = "Toolbar - Dark",
    group = "DashboardToolbar",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DashboardToolbarPreview(
    @PreviewParameter(ToolbarTitleProvider::class) title: String
) {
    MonashTheme {
        Surface {
            DashboardToolbar(title = title)
        }
    }
}

@Preview(
    name = "Toolbar - Long Text",
    group = "DashboardToolbar",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun DashboardToolbarLongTextPreview() {
    MonashTheme {
        Surface {
            DashboardToolbar(title = "Hey, Christopher Alexander")
        }
    }
}

@Preview(
    name = "Toolbar - Accessibility (Large Font)",
    group = "DashboardToolbar",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun DashboardToolbarAccessibilityPreview() {
    MonashTheme {
        Surface {
            DashboardToolbar(title = "Hey, Kier")
        }
    }
}
