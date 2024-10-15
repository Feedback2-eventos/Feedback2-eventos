package com.example.feedback2_eventos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Usuario.UsuarioRepository
import kotlinx.coroutines.delay

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
    var cocinaEncendido by remember { mutableStateOf(cocina?.encendido ?: false) }
    var cocinaConsumo by remember { mutableStateOf(cocina?.consumo() ?: 0.0) }
    var totalConsumo by remember { mutableStateOf(0.0) }

    LaunchedEffect(cocinaEncendido) {
        while (cocinaEncendido) {
            delay(2000)
            cocinaConsumo += cocina?.calculateConsumoIncrement() ?: 0.0
            totalConsumo += cocina?.calculateConsumoIncrement() ?: 0.0
        }
    }

    LaunchedEffect(Unit) {
        val usuarioRepository = UsuarioRepository()
        usuarioRepository.obtenerUsuario(username) { usuario ->
            if (usuario != null) {
                totalConsumo = usuario.consumo + cocinaConsumo
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Volver")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { showSalonConsumo = !showSalonConsumo }) {
            Text("Mostrar Consumo del Salón")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showCocinaConsumo = !showCocinaConsumo }) {
            Text("Mostrar Consumo de la Cocina")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDormitorioConsumo = !showDormitorioConsumo }) {
            Text("Mostrar Consumo del Dormitorio")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (showSalonConsumo) {
            Text("Consumo del Salón: ${salon?.consumo() ?: 0.0}")
        }

        if (showCocinaConsumo) {
            Text("Consumo de la Cocina: $cocinaConsumo")
            Text("Consumo Total: $totalConsumo")
            Text("Atributos de la Cocina: ${cocina?.let { "Nevera: ${it.tieneNevera}, Horno: ${it.tieneHorno}, Vitrocerámica: ${it.tieneVitroceramica}, Encendido: ${it.encendido}" } ?: ""}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                cocinaEncendido = !cocinaEncendido
                cocina?.updateEncendido(cocinaEncendido)
            }) {
                Text(if (cocinaEncendido) "Apagar" else "Encender")
            }
        }

        if (showDormitorioConsumo) {
            Text("Consumo del Dormitorio: ${dormitorio?.consumo() ?: 0.0}")
            Text("Atributos del Dormitorio: ${dormitorio?.let { "Altavoces: ${it.tieneAltavoces}, Lamparilla: ${it.tieneLamparilla}, Ordenador: ${it.tieneOrdenador}, Encendido: ${it.encendido}" } ?: ""}")
        }

        Spacer(modifier = Modifier.height(8.dp))
       Button(onClick = {
    guardarConsumo(username, totalConsumo)
}) {
    Text("Guardar Consumo")
}
    }
}

fun guardarConsumo(username: String, totalConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoUsuario(username, totalConsumo)
}
