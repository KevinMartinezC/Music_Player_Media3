package com.example.musicplayer.component.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.musicplayer.R


@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = progress,
            onValueChange = {},
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_8dp))
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_16dp))
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
    }
}