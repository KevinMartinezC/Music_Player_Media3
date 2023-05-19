package com.example.musicplayer.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.SoundResult
import com.example.musicplayer.R


@Composable
fun ResultCard(animeItem: SoundResult, grid: Boolean) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),

        ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(animeItem.images.waveformM),
                contentDescription = stringResource(R.string.image_song),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .aspectRatio(if (grid) 2f else  3f),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = animeItem.name,
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(if (grid) 3f else 5f),
            )
        }
    }
}
