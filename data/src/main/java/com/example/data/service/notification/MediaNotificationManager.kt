package com.example.data.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.example.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MediaNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val player: ExoPlayer
) {

    private var notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        createNotificationChannel()
    }

    @UnstableApi
    fun startNotificationService(
        mediaSessionService: MediaSessionService,
        mediaSession: MediaSession,
        pendingIntent: PendingIntent
    ) {
        buildNotification(mediaSession,pendingIntent)
        startForegroundNotification(mediaSessionService)
    }

    @UnstableApi
    private fun buildNotification(mediaSession: MediaSession, pendingIntent: PendingIntent) {

        PlayerNotificationManager.Builder(context, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID)
            .setMediaDescriptionAdapter(
                MediaNotificationAdapter(
                    context = context,
                    pendingIntent = pendingIntent
                )
            )
            .setSmallIconResourceId(R.drawable.ic_microphone)
            .build()
            .also {
                it.setMediaSessionToken(mediaSession.sessionCompatToken)
                it.setUseFastForwardActionInCompactView(true)
                it.setUseRewindActionInCompactView(true)
                it.setUseNextActionInCompactView(false)
                it.setUsePreviousActionInCompactView(false)
                it.setPriority(NotificationCompat.PRIORITY_LOW)
                it.setPlayer(player)
            }
    }

    private fun startForegroundNotification(mediaSessionService: MediaSessionService) {
        val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        mediaSessionService.startForeground(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    companion object{
        private const val NOTIFICATION_ID = 200
        private const val NOTIFICATION_CHANNEL_NAME = "notification channel 1"
        private const val NOTIFICATION_CHANNEL_ID = "notification channel id 1"
    }
}
