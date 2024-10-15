// app/src/main/java/com/example/feedback2_eventos/MenuScreen.kt
package com.example.feedback2_eventos

import android.util.Log
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

    LaunchedEffect(cocinaEncendido) {
        while (cocinaEncendido) {
            cocinaConsumo = cocina?.consumo() ?: 0.0
            delay(1000) // Update the UI every second
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { showSalonConsumo = !showSalonConsumo }) {
            Text("Mostrar Consumo Salón")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showCocinaConsumo = !showCocinaConsumo }) {
            Text("Mostrar Consumo Cocina")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDormitorioConsumo = !showDormitorioConsumo }) {
            Text("Mostrar Consumo Dormitorio")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (cocina != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Encendido Cocina")
                Switch(checked = cocinaEncendido, onCheckedChange = {
                    cocinaEncendido = it
                    cocina.updateEncendido(it)
                })
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (showSalonConsumo) {
            Text("Consumo del Salón: ${salon?.consumo() ?: 0.0}")
            Text("Atributos del Salón: ${salon?.let { "Lampara: ${it.tieneLampara}, Televisión: ${it.tieneTelevision}, Aire Acondicionado: ${it.tieneAireAcondicionado}, Encendido: ${it.encendido}" } ?: ""}")
        }

        if (showCocinaConsumo) {
            Text("Consumo de la Cocina: $cocinaConsumo")
            Text("Atributos de la Cocina: ${cocina?.let { "Nevera: ${it.tieneNevera}, Horno: ${it.tieneHorno}, Vitrocerámica: ${it.tieneVitroceramica}, Encendido: ${it.encendido}" } ?: ""}")
        }

        if (showDormitorioConsumo) {
            Text("Consumo del Dormitorio: ${dormitorio?.consumo() ?: 0.0}")
            Text("Atributos del Dormitorio: ${dormitorio?.let { "Altavoces: ${it.tieneAltavoces}, Lamparilla: ${it.tieneLamparilla}, Ordenador: ${it.tieneOrdenador}, Encendido: ${it.encendido}" } ?: ""}")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            guardarConsumo(username, cocinaConsumo)
        }) {
            Text("Guardar Consumo")
        }
    }
}

fun guardarConsumo(username: String, consumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoUsuario(username, consumo)
}