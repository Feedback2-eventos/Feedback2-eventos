// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                    })
                } else {
                    MainContent(hasCocinas = hasCocinas, username = username, initialCocina = initialCocina)
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, hasCocinas: Boolean, username: String, initialCocina: Cocina?) {
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

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Asegúrate de que fondo.jpg esté en res/drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                AnimatedButton(text = "Agregar Salón", onClick = { showSalonForm = true })
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedButton(text = "Agregar Cocina", onClick = { showCocinaForm = true })
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedButton(text = "Agregar Dormitorio", onClick = { showDormitorioForm = true })
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedButton(text = "Mostrar Menú", onClick = { showMenuScreen = true })

                if (showSalonForm) {
                    SalonForm(username = username, onSubmit = { salon = it; showSalonForm = false })
                }

                if (showCocinaForm) {
                    CocinaForm(username = username, onSubmit = { cocina = it; showCocinaForm = false })
                }

                if (showDormitorioForm) {
                    DormitorioForm(username = username, onSubmit = { dormitorio = it; showDormitorioForm = false })
                }
            }
        }
    }
}