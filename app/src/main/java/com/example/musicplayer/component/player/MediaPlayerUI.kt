package com.example.musicplayer.component.player

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import com.example.musicplayer.R
import com.example.musicplayer.component.player.utils.UIEvent


@Composable
fun MediaPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val aspectRatio = if (isLandscape) 4.5f else 3f

    Box(
        modifier = modifier
            .padding(
                dimensionResource(id = R.dimen.padding_30dp))
            .aspectRatio(aspectRatio)
            .shadow(
                elevation = dimensionResource(id = R.dimen.elevation_8dp),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_8dp))
            )
            .background(Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerBar(
                progress = progress,
                durationString = durationString,
                progressString = progressString,
            )
            PlayerControls(
                playResourceProvider = playResourceProvider,
                onUiEvent = onUiEvent
            )
        }
    }
}
