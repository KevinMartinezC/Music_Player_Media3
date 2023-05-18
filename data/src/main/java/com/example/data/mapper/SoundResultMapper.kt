package com.example.data.mapper

import com.example.data.ApiSoundResult
import com.example.domain.Images
import com.example.domain.Previews
import com.example.domain.SoundResult

fun ApiSoundResult.toSoundResult(): SoundResult{
    return SoundResult(
        id = id,
        name = name,
        username = username,
        previews = Previews(previews.previewHqMp3),
        images = Images(images.waveformM)
    )
}