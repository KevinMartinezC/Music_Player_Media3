package com.example.musicplayer.component.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicplayer.R
import com.example.musicplayer.component.player.utils.UIEvent

@Composable
internal fun PlayerControls(
    playResourceProvider: () -> Int,
    onUiEvent: (UIEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_35dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(android.R.drawable.ic_media_rew),
            contentDescription = stringResource(R.string.backward_button),
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.Backward) })
                .padding(dimensionResource(id = R.dimen.padding_12dp))
                .size(dimensionResource(id = R.dimen.size_34dp))
        )
        Image(
            painter = painterResource(id = playResourceProvider()),
            contentDescription = stringResource(R.string.play_pause_button),
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.PlayPause) })
                .padding(dimensionResource(id = R.dimen.padding_8dp))
                .size(dimensionResource(id = R.dimen.size_56dp))
        )
        Icon(
            painter = painterResource(android.R.drawable.ic_media_ff),
            contentDescription = stringResource(R.string.forward_button),
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.Forward) })
                .padding(dimensionResource(id = R.dimen.padding_12dp))
                .size(dimensionResource(id = R.dimen.size_34dp))
        )
    }
}