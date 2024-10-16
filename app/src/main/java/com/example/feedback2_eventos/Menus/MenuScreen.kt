package com.example.feedback2_eventos.Menus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Usuario.Usuario
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
    var salonEncendido by remember { mutableStateOf(salon?.encendido ?: false) }
    var salonConsumo by remember { mutableStateOf(salon?.consumo() ?: 0.0) }
    var dormitorioEncendido by remember { mutableStateOf(dormitorio?.encendido ?: false) }
    var dormitorioConsumo by remember { mutableStateOf(dormitorio?.consumo() ?: 0.0) }
    var totalConsumo by remember { mutableStateOf(0.0) }

    LaunchedEffect(cocinaEncendido) {
        while (cocinaEncendido) {
            delay(2000)
            cocinaConsumo += cocina?.calculateConsumoIncrement() ?: 0.0
            totalConsumo += cocina?.calculateConsumoIncrement() ?: 0.0
        }
    }

    LaunchedEffect(salonEncendido) {
        while (salonEncendido) {
            delay(2000)
            salonConsumo += salon?.calculateConsumoIncrement() ?: 0.0
            totalConsumo += salon?.calculateConsumoIncrement() ?: 0.0
        }
    }

    LaunchedEffect(dormitorioEncendido) {
        while (dormitorioEncendido) {
            delay(2000)
            dormitorioConsumo += dormitorio?.calculateConsumoIncrement() ?: 0.0
            totalConsumo += dormitorio?.calculateConsumoIncrement() ?: 0.0
        }
    }

    LaunchedEffect(Unit) {
        val usuarioRepository = UsuarioRepository()
        usuarioRepository.obtenerUsuario(username) { usuario: Usuario? ->
            if (usuario != null) {
                totalConsumo = usuario.consumo + cocinaConsumo + salonConsumo + dormitorioConsumo
            }
        }
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
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
                Text("Consumo del Salón: $salonConsumo")
                Text("Consumo Total: $totalConsumo")
                Text("Atributos del Salón: ${salon?.let { "Televisión: ${it.tieneTelevision}, Lámpara: ${it.tieneLampara}, Aire Acondicionado: ${it.tieneAireAcondicionado}, Encendido: ${it.encendido}" } ?: ""}")
                Button(onClick = {
                    salonEncendido = !salonEncendido
                    salon?.updateEncendido(salonEncendido)
                }) {
                    Text(if (salonEncendido) "Apagar" else "Encender")
                }
                Spacer(modifier = Modifier.height(8.dp))
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
                Text("Consumo del Dormitorio: $dormitorioConsumo")
                Text("Consumo Total: $totalConsumo")
                Text("Atributos del Dormitorio: ${dormitorio?.let { "Altavoces: ${it.tieneAltavoces}, Lamparilla: ${it.tieneLamparilla}, Ordenador: ${it.tieneOrdenador}, Encendido: ${it.encendido}" } ?: ""}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    dormitorioEncendido = !dormitorioEncendido
                    dormitorio?.updateEncendido(dormitorioEncendido)
                }) {
                    Text(if (dormitorioEncendido) "Apagar" else "Encender")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                guardarConsumo(username, totalConsumo)
            }) {
                Text("Guardar Consumo Total")
            }
        }
    }
}

fun guardarConsumoDormitorio(username: String, dormitorioConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoDormitorio(username, dormitorioConsumo)
}

fun guardarConsumo(username: String, totalConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoUsuario(username, totalConsumo)
}