package com.example.safetynet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HistoryScreen(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val mockHeartRateData: List<Pair<Int, Double>> = listOf(
            0 to 72.0,
            1 to 75.0,
            2 to 78.5,
            3 to 80.0,
            4 to 76.5,
            5 to 74.0,
            6 to 77.2,
            7 to 81.0,
            8 to 79.5,
            9 to 76.8,
            10 to 74.0
        )
        LineChart(data = mockHeartRateData, modifier = modifier)
    }
}