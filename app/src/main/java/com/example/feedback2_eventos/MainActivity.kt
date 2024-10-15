// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.feedback2_eventos.Salon.SalonForm
import com.example.feedback2_eventos.Salon.SalonRepository
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Cocina.CocinaForm
import com.example.feedback2_eventos.Cocina.CocinaRepository
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.DormitorioForm
import com.example.feedback2_eventos.Dormitorio.DormitorioRepository
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.ui.theme.Feedback2eventosTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initializeNotifications()
        } else {
            showPermissionDeniedMessage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback2eventosTheme {
                MainContent()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    initializeNotifications()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showPermissionRationale()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            initializeNotifications()
        }
    }

    private fun initializeNotifications() {
        val channelId = "consumo_peligroso_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Consumo Peligroso"
            val descriptionText = "Notificaciones de consumo peligroso"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showPermissionDeniedMessage() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }

    private fun showPermissionRationale() {
        Toast.makeText(this, "Notification permission is needed to alert you of dangerous consumption levels", Toast.LENGTH_LONG).show()
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var showSalonForm by remember { mutableStateOf(false) }
    var showCocinaForm by remember { mutableStateOf(false) }
    var showDormitorioForm by remember { mutableStateOf(false) }
    var showMenuScreen by remember { mutableStateOf(false) }
    var showConsumoPeligrosoScreen by remember { mutableStateOf(false) }
    var salon by remember { mutableStateOf<Salon?>(null) }
    var cocina by remember { mutableStateOf<Cocina?>(null) }
    var dormitorio by remember { mutableStateOf<Dormitorio?>(null) }

    if (showConsumoPeligrosoScreen) {
        ConsumoPeligrosoScreen(
            salon = salon,
            cocina = cocina,
            dormitorio = dormitorio,
            onSave = { _, _, _ -> },
            onBack = { showConsumoPeligrosoScreen = false }
        )
    } else if (showMenuScreen) {
        MenuScreen(
            salon = salon,
            cocina = cocina,
            dormitorio = dormitorio,
            onShowSalonConsumo = { salon?.let { sendNotification(context, roomName = it.nombre, consumo = it.consumo()) } },
            onShowCocinaConsumo = { cocina?.let { sendNotification(context, roomName = it.nombre, consumo = it.consumo()) } },
            onShowDormitorioConsumo = { dormitorio?.let { sendNotification(context, roomName = it.nombre, consumo = it.consumo()) } },
            onBack = { showMenuScreen = false },
            context = context
        )
    } else {
        Column(modifier = modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showMenuScreen = true }) {
                Text("Show Menu")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showConsumoPeligrosoScreen = true }) {
                Text("Show Consumo Peligroso")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showSalonForm = true }) {
                Text("Add Salon")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showCocinaForm = true }) {
                Text("Add Cocina")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showDormitorioForm = true }) {
                Text("Add Dormitorio")
            }

            if (showSalonForm) {
                SalonForm(onSubmit = { salon = it; showSalonForm = false })
            }

            if (showCocinaForm) {
                CocinaForm(onSubmit = { cocina = it; showCocinaForm = false })
            }

            if (showDormitorioForm) {
                DormitorioForm(onSubmit = { dormitorio = it; showDormitorioForm = false })
            }
        }
    }
}