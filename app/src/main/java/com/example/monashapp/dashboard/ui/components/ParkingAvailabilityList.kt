package com.example.monashapp.dashboard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.monashapp.R
import com.example.monashapp.core.model.dashboard.ParkingAvailability
import com.example.monashapp.dashboard.ui.DashboardColors
import com.example.monashapp.dashboard.ui.DashboardSpacing

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