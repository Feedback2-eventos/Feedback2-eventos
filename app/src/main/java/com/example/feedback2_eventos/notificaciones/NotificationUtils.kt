// app/src/main/java/com/example/feedback2_eventos/NotificationUtils.kt
package com.example.feedback2_eventos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun sendNotification(context: Context, roomName: String, consumo: Double) {
    val channelId = "consumo_peligroso_channel"
    val notificationId = 1

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Consumo Peligroso"
        val descriptionText = "Notificaciones de consumo peligroso"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_warning)
        .setContentTitle("Consumo Peligroso en $roomName")
        .setContentText("El consumo ha alcanzado $consumo")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, notification)
    }
}