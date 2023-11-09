package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme

@Composable
fun LoadingIndicator() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
            .height(4.dp),
        color = theme.loadingIndicator,
        trackColor = theme.loadingIndicatorTrack,
    )
}