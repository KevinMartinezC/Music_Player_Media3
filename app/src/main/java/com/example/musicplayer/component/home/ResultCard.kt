package com.example.musicplayer.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.SoundResult
import com.example.musicplayer.R


@Composable
fun ResultCard(
    musicItem: SoundResult,
    grid: Boolean,
    navController: NavHostController
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(("detail/${musicItem.id}"))
            },
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(musicItem.images.waveformM),
                contentDescription = stringResource(R.string.image_song),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .aspectRatio(if (grid) 2f else 3f),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = musicItem.name,
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(if (grid) 3f else 5f),
            )
        }
    }
}
