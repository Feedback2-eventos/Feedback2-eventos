package com.example.feedback2_eventos.Menus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.AnimatedButton
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
    var initialConsumo by remember { mutableStateOf(0.0) }

    // Fetch the initial total consumption once when the component is first mounted
    LaunchedEffect(Unit) {
        val usuarioRepository = UsuarioRepository()
        usuarioRepository.obtenerUsuario(username) { usuario: Usuario? ->
            if (usuario != null) {
                initialConsumo = usuario.consumo
                totalConsumo = maxOf(initialConsumo + cocinaConsumo + salonConsumo + dormitorioConsumo, usuario.consumo)
            }
        }
    }

    LaunchedEffect(cocinaEncendido) {
        while (cocinaEncendido) {
            delay(2000)
            val incremento = cocina?.calculateConsumoIncrement() ?: 0.0
            cocinaConsumo += incremento
            totalConsumo = initialConsumo + cocinaConsumo + salonConsumo + dormitorioConsumo
        }
    }

    LaunchedEffect(salonEncendido) {
        while (salonEncendido) {
            delay(2000)
            val incremento = salon?.calculateConsumoIncrement() ?: 0.0
            salonConsumo += incremento
            totalConsumo = initialConsumo + cocinaConsumo + salonConsumo + dormitorioConsumo
        }
    }

    LaunchedEffect(dormitorioEncendido) {
        while (dormitorioEncendido) {
            delay(2000)
            val incremento = dormitorio?.calculateConsumoIncrement() ?: 0.0
            dormitorioConsumo += incremento
            totalConsumo = initialConsumo + cocinaConsumo + salonConsumo + dormitorioConsumo
        }
    }

   LaunchedEffect(Unit) {
    while (true) {
        if (totalConsumo > 0) {
            guardarConsumo(username, totalConsumo)
        }
        delay(2000) // Espera 2 segundos antes de volver a ejecutar
    }
}
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            AnimatedButton(text = "Volver", onClick = onBack)
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedButton(text = "Mostrar Consumo del Salón", onClick = {
                showSalonConsumo = !showSalonConsumo
            })
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedButton(text = "Mostrar Consumo de la Cocina", onClick = {
                showCocinaConsumo = !showCocinaConsumo
            })
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedButton(text = "Mostrar Consumo del Dormitorio", onClick = {
                showDormitorioConsumo = !showDormitorioConsumo
            })
            Spacer(modifier = Modifier.height(16.dp))

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
        }
    }
}

fun guardarConsumo(username: String, totalConsumo: Double) {
    val usuarioRepository = UsuarioRepository()
    usuarioRepository.actualizarConsumoUsuario(username, totalConsumo)
}