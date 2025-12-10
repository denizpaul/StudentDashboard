package com.example.monashapp.dashboard.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.monashapp.ui.theme.MonashTheme

/**
 * CardTile - Bold section header component
 *
 * Purpose: Display bold, large text headers for major sections like date headers
 *
 * @param title The text to display as the section header
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun CardTile(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f), // 90% opacity per Figma
        modifier = modifier
            .fillMaxWidth()
            .semantics { heading() } // Mark as heading for screen reader navigation
    )
}

// Preview parameter provider
private class CardTitleProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Today, 10 March",
        "Sun, 12 March",
        "Mon, 13 March",
        "This Week"
    )
}

@Preview(
    name = "CardTile - Light",
    group = "CardTile",
    showBackground = true
)
@Preview(
    name = "CardTile - Dark",
    group = "CardTile",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CardTilePreview(
    @PreviewParameter(CardTitleProvider::class) title: String
) {
    MonashTheme {
        Surface {
            CardTile(title = title)
        }
    }
}

@Preview(
    name = "CardTile - Long Text",
    group = "CardTile",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun CardTileLongTextPreview() {
    MonashTheme {
        Surface {
            CardTile(title = "Wednesday, 15 March 2025")
        }
    }
}

@Preview(
    name = "CardTile - Accessibility",
    group = "CardTile",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun CardTileAccessibilityPreview() {
    MonashTheme {
        Surface {
            CardTile(title = "Today, 10 March")
        }
    }
}

