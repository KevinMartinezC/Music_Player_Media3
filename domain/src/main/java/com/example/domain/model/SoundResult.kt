package com.example.domain.model

data class SoundResult(
    val id: Int,
    val name: String,
    val username: String,
    val previews: Previews,
    val images: Images
)

data class Previews(
    val previewHqMp3: String
)

data class Images(
    val waveformM: String
)



