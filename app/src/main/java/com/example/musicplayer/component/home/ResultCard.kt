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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.SoundResult
import com.example.musicplayer.R

private const val GRID_ASPECT_RATIO = 2f
private const val NORMAL_ASPECT_RATIO = 3f
private const val TEXT_GRID_ASPECT_RATIO = 3f
private const val TEXT_NORMAL_ASPECT_RATIO = 5f

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
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_8dp)),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_8dp))
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
                    .padding(dimensionResource(id = R.dimen.padding_8dp))
                    .aspectRatio(if (grid) GRID_ASPECT_RATIO else NORMAL_ASPECT_RATIO),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = musicItem.name,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_16dp))
                    .aspectRatio(if (grid) TEXT_GRID_ASPECT_RATIO else TEXT_NORMAL_ASPECT_RATIO),
            )
        }
    }
}
