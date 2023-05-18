package com.example.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ApiSoundResult>
)

data class ApiSoundResult(
    val id: Int,
    val name: String,
    val username: String,
    val previews: Previews,
    val images: Images
)

data class Previews(
    @SerializedName("preview-hq-mp3")
    val previewHqMp3: String,
)

data class Images(
    @SerializedName("waveform_m")
    val waveformM: String,
)
