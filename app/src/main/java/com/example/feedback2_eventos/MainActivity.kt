// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Salon.SalonForm
import com.example.feedback2_eventos.Cocina.CocinaForm
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.Dormitorio.DormitorioForm
import com.example.feedback2_eventos.Menus.MenuScreen
import com.example.feedback2_eventos.Menus.StartScreen
import com.example.feedback2_eventos.ui.theme.Feedback2eventosTheme
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Usuario.UsuarioRepository
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private val CHANNEL_ID = "consumo_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel()
        requestNotificationPermission() // Request notification permission
        setContent {
            Feedback2eventosTheme {
                var showStartScreen by remember { mutableStateOf(true) }
                var hasCocinas by remember { mutableStateOf(false) }
                var username by remember { mutableStateOf("") }
                var initialCocina by remember { mutableStateOf<Cocina?>(null) }

                if (showStartScreen) {
                    StartScreen(onStart = { hasCocinasResult: Boolean, user: String, cocina: Cocina? ->
                        hasCocinas = hasCocinasResult
                        username = user
                        initialCocina = cocina
                        showStartScreen = false
                        checkConsumoAndNotify(username)
                    })
                } else {
                    MainContent(
                        hasCocinas = hasCocinas,
                        username = username,
                        initialCocina = initialCocina
                    )
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                enableVibration(true)
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    fun showNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Consumo Alto")
            .setContentText("El consumo total ha alcanzado los 10000")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Ensures sound, vibration, and lights

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission if not granted
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
                return
            }
            notify(1, builder.build())
        }
    }

    private fun checkConsumoAndNotify(username: String) {
    val usuarioRepository = UsuarioRepository()
    val scope = CoroutineScope(Dispatchers.IO)
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    val notificationId = 1

    scope.launch {
        while (true) {
            usuarioRepository.obtenerUsuario(username) { usuario ->
                usuario?.let {
                    if (it.consumo > 1000 && !isNotificationActive(notificationManager, notificationId)) {
                        showNotification()
                    }
                }
            }
            delay(50) // Espera 5 segundos antes de volver a comprobar
        }
    }
}

private fun isNotificationActive(notificationManager: NotificationManager, notificationId: Int): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        for (notification in notificationManager.activeNotifications) {
            if (notification.id == notificationId) {
                return true
            }
        }
    }
    return false
}

    @Composable
    fun MainContent(
        modifier: Modifier = Modifier,
        hasCocinas: Boolean,
        username: String,
        initialCocina: Cocina?
    ) {
        var showSalonForm by remember { mutableStateOf(false) }
        var showCocinaForm by remember { mutableStateOf(false) }
        var showDormitorioForm by remember { mutableStateOf(false) }
        var showMenuScreen by remember { mutableStateOf(false) }
        var salon by remember { mutableStateOf<Salon?>(null) }
        var cocina by remember { mutableStateOf(initialCocina) }
        var dormitorio by remember { mutableStateOf<Dormitorio?>(null) }
        var hasSalon by remember { mutableStateOf(false) }
        var hasDormitorio by remember { mutableStateOf(false) }

        LaunchedEffect(username) {
            val usuarioRepository = UsuarioRepository()
            usuarioRepository.obtenerUsuario(username) { usuario ->
                hasSalon = usuario?.salones?.isNotEmpty() == true
                hasDormitorio = usuario?.dormitorios?.isNotEmpty() == true
            }
        }

        if (showMenuScreen) {
            MenuScreen(
                username = username,
                salon = salon,
                cocina = cocina,
                dormitorio = dormitorio,
                onShowSalonConsumo = { /* Implement logic to show salon consumption */ },
                onShowCocinaConsumo = { /* Implement logic to show kitchen consumption */ },
                onShowDormitorioConsumo = { /* Implement logic to show bedroom consumption */ },
                onBack = { showMenuScreen = false }
            )
        } else {
            Column(modifier = modifier.padding(16.dp)) {
                Button(onClick = { showSalonForm = true }, enabled = !hasSalon) {
                    Text("Agregar Salón")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showCocinaForm = true }, enabled = !hasCocinas) {
                    Text("Agregar Cocina")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showDormitorioForm = true }, enabled = !hasDormitorio) {
                    Text("Agregar Dormitorio")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showMenuScreen = true }) {
                    Text("Mostrar Menú")
                }

                if (showSalonForm) {
                    SalonForm(username = username, onSubmit = { salon = it; showSalonForm = false })
                }

                if (showCocinaForm) {
                    CocinaForm(
                        username = username,
                        onSubmit = { cocina = it; showCocinaForm = false })
                }

                if (showDormitorioForm) {
                    DormitorioForm(
                        username = username,
                        onSubmit = { dormitorio = it; showDormitorioForm = false })
                }
            }
        }
    }
}