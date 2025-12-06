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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.dashboard.ui.DashboardColors
import com.example.monashapp.dashboard.ui.DashboardSpacing
import com.example.monashapp.ui.theme.MonashTheme

@Composable
fun ParkingAvailabilityList(parkingAvailability: List<ParkingAvailability>) {
    parkingAvailability.forEach { lot ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = DashboardSpacing.smallSpacing),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = lot.zoneName, style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                PermitBadge(label = stringResource(id = R.string.dashboard_permit_blue_label), value = lot.bluePermitAvailable, color = DashboardColors.parkingBlue)
                PermitBadge(label = stringResource(id = R.string.dashboard_permit_red_label), value = lot.redPermitAvailable, color = DashboardColors.parkingRed)
            }
        }
    }
}

@Composable
private fun PermitBadge(label: String, value: Int, color: Color) {
    Row(horizontalArrangement = Arrangement.spacedBy(DashboardSpacing.smallSpacing)) {
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier
                .background(color, CircleShape)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
        Text(text = value.toString(), fontWeight = FontWeight.Bold)
    }
}

// Preview parameter provider for different parking scenarios
private class ParkingListProvider : PreviewParameterProvider<List<ParkingAvailability>> {
    override val values = sequenceOf(
        // Typical scenario - mixed availability
        listOf(
            ParkingAvailability(id = "parking_1", zoneName = "North (multi-level)", bluePermitAvailable = 12, redPermitAvailable = 5),
            ParkingAvailability(id = "parking_2", zoneName = "West 1", bluePermitAvailable = 0, redPermitAvailable = 2)
        ),
        // All full
        listOf(
            ParkingAvailability(id = "parking_3", zoneName = "South", bluePermitAvailable = 0, redPermitAvailable = 0),
            ParkingAvailability(id = "parking_4", zoneName = "East", bluePermitAvailable = 0, redPermitAvailable = 0)
        ),
        // High availability
        listOf(
            ParkingAvailability(id = "parking_5", zoneName = "Campus Center", bluePermitAvailable = 150, redPermitAvailable = 89)
        ),
        // Single lot
        listOf(
            ParkingAvailability(id = "parking_6", zoneName = "Visitor Parking", bluePermitAvailable = 5, redPermitAvailable = 3)
        )
    )
}

@Preview(
    name = "Parking List - Light",
    group = "ParkingAvailabilityList",
    showBackground = true
)
@Preview(
    name = "Parking List - Dark",
    group = "ParkingAvailabilityList",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun ParkingAvailabilityListPreview(
    @PreviewParameter(ParkingListProvider::class, limit = 2) parkingList: List<ParkingAvailability>
) {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(parkingAvailability = parkingList)
        }
    }
}

@Preview(
    name = "Parking - Typical Availability",
    group = "ParkingAvailabilityList - States",
    showBackground = true
)
@Composable
private fun ParkingAvailabilityTypicalPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(id = "1", zoneName = "North (multi-level)", bluePermitAvailable = 12, redPermitAvailable = 5),
                    ParkingAvailability(id = "2", zoneName = "West 1", bluePermitAvailable = 0, redPermitAvailable = 2)
                )
            )
        }
    }
}

@Preview(
    name = "Parking - All Full",
    group = "ParkingAvailabilityList - States",
    showBackground = true
)
@Composable
private fun ParkingAvailabilityFullPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(id = "1", zoneName = "South", bluePermitAvailable = 0, redPermitAvailable = 0),
                    ParkingAvailability(id = "2", zoneName = "East", bluePermitAvailable = 0, redPermitAvailable = 0)
                )
            )
        }
    }
}

@Preview(
    name = "Parking - High Availability",
    group = "ParkingAvailabilityList - States",
    showBackground = true
)
@Composable
private fun ParkingAvailabilityHighPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(id = "1", zoneName = "Campus Center", bluePermitAvailable = 150, redPermitAvailable = 89)
                )
            )
        }
    }
}

@Preview(
    name = "Parking - Long Zone Name",
    group = "ParkingAvailabilityList - States",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ParkingAvailabilityLongNamePreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(
                        id = "1",
                        zoneName = "North Campus Multi-level Parking Structure",
                        bluePermitAvailable = 12,
                        redPermitAvailable = 5
                    )
                )
            )
        }
    }
}

@Preview(
    name = "Parking - Accessibility (Large Font)",
    group = "ParkingAvailabilityList",
    showBackground = true,
    fontScale = 1.5f
)
@Composable
private fun ParkingAvailabilityAccessibilityPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(id = "1", zoneName = "North (multi-level)", bluePermitAvailable = 12, redPermitAvailable = 5)
                )
            )
        }
    }
}

@Preview(
    name = "Parking - Small Screen",
    group = "ParkingAvailabilityList",
    showBackground = true,
    widthDp = 320
)
@Composable
private fun ParkingAvailabilitySmallScreenPreview() {
    MonashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            ParkingAvailabilityList(
                parkingAvailability = listOf(
                    ParkingAvailability(id = "1", zoneName = "North", bluePermitAvailable = 12, redPermitAvailable = 5)
                )
            )
        }
    }
}
