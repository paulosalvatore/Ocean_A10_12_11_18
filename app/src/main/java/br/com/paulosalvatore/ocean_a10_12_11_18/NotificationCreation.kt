package br.com.paulosalvatore.ocean_a10_12_11_18

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

object NotificationCreation {
    const val NOTIFY_ID = 1000
    private val vibration = longArrayOf(300, 400, 500, 400, 300)

    private var notificationManager: NotificationManager? = null

    // Channel Information
    private const val CHANNEL_ID = "OceanPush_1"
    private const val CHANNEL_NAME = "OceanPush - Push Channel 1"
    private const val CHANNEL_DESCRIPTION = "OceanPush - Used for main notifications"

    fun createNotification(context: Context, title: String, body: String) {
        if (notificationManager == null)
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = notificationManager?.getNotificationChannel(CHANNEL_ID)

            if (channel == null) {
                channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

                channel.description = CHANNEL_DESCRIPTION
                channel.enableVibration(true)
                channel.enableLights(true)
                channel.vibrationPattern = vibration

                notificationManager?.createNotificationChannel(channel)
            }
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setTicker(title)
            .setVibrate(vibration)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setContentIntent(pendingIntent)

        val notificationApp = builder.build()
        notificationManager?.notify(NOTIFY_ID, notificationApp)
    }
}
