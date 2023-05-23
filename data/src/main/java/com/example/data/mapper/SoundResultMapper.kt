package com.example.data.mapper

import com.example.data.service.api.ApiSoundResult
import com.example.domain.model.Images
import com.example.domain.model.Previews
import com.example.domain.model.SoundResult

fun ApiSoundResult.toSoundResult(): SoundResult {
    return SoundResult(
        id = id,
        name = name,
        username = username,
        previews = Previews(previews.previewHqMp3),
        images = Images(images.waveformM)
    )
}