package com.example.musicplayer.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.UIEvent

@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (UIEvent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value =  progress,
            onValueChange = {},
            onValueChangeFinished = {},
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
    }
}