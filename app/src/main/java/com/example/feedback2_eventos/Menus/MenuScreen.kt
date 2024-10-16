package com.example.feedback2_eventos.Menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Importación añadida
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.R
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Usuario.UsuarioRepository
import com.example.feedback2_eventos.AnimatedButton

@Composable
fun MenuScreen(
    username: String,
    salon: Salon?,
    cocina: Cocina?,
    dormitorio: Dormitorio?,
    onShowSalonConsumo: () -> Unit,
    onShowCocinaConsumo: () -> Unit,
    onShowDormitorioConsumo: () -> Unit,
    onBack: () -> Unit
) {
    var showSalonConsumo by remember { mutableStateOf(false) }
    var showCocinaConsumo by remember { mutableStateOf(false) }
    var showDormitorioConsumo by remember { mutableStateOf(false) }
    var totalConsumo by remember { mutableStateOf(0.0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.home), // Asegúrate de que home.jpg esté en res/drawable
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    AnimatedButton(
                        text = "Volver",
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedButton(
                        text = "Mostrar Consumo del Salón",
                        onClick = { showSalonConsumo = !showSalonConsumo },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedButton(
                        text = "Mostrar Consumo de la Cocina",
                        onClick = { showCocinaConsumo = !showCocinaConsumo },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedButton(
                        text = "Mostrar Consumo del Dormitorio",
                        onClick = { showDormitorioConsumo = !showDormitorioConsumo },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (showSalonConsumo) {
                        Text("Consumo del Salón: ${salon?.consumo()}")
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (showCocinaConsumo) {
                        Text("Consumo de la Cocina: ${cocina?.consumo()}")
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (showDormitorioConsumo) {
                        Text("Consumo del Dormitorio: ${dormitorio?.consumo()}")
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedButton(
                        text = "Guardar Consumo Total",
                        onClick = { guardarConsumo(username, totalConsumo) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
                    )
                }
            }
        }
    }
}

fun guardarConsumo(username: String, totalConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoUsuario(username, totalConsumo)
}

fun guardarConsumoDormitorio(username: String, dormitorioConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoDormitorio(username, dormitorioConsumo)
}