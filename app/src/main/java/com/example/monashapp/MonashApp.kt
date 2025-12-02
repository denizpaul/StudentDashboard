package com.example.monashapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.monashapp.dashboard.ui.DashboardRoute
import com.example.monashapp.ui.theme.MonashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonashTheme {
                DashboardRoute()
            }
        }
    }
}

