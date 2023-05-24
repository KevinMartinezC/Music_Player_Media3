package com.example.musicplayer.ui.component.player

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme


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

@Preview
@Composable
fun PlayerBarPreview() {
    MusicPlayerTheme {
        PlayerBar(
            progress = 0.5f,
            progressString = "01:30",
            durationString = "03:00"
        )
    }
}
