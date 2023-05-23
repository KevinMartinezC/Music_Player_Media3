package com.example.data.repositoryImpl

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.data.service.media.MediaService
import com.example.domain.repository.MediaServiceHandlerRepository
import javax.inject.Inject

class MediaRepositoryImplRepository @Inject constructor(
    private val context: Context
) : MediaServiceHandlerRepository {

    private var isServiceRunning = false
    override fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(context, MediaService::class.java)
            ContextCompat.startForegroundService(context, intent)
            isServiceRunning = true
        }
    }
}
