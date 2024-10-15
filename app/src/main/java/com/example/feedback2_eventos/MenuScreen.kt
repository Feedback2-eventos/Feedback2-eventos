// app/src/main/java/com/example/feedback2_eventos/MenuScreen.kt
package com.example.feedback2_eventos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.Salon.Salon

@Composable
fun MenuScreen(
    salon: Salon?,
    cocina: Cocina?,
    dormitorio: Dormitorio?,
    onShowSalonConsumo: () -> Unit,
    onShowCocinaConsumo: () -> Unit,
    onShowDormitorioConsumo: () -> Unit
) {
    var showSalonConsumo by remember { mutableStateOf(false) }
    var showCocinaConsumo by remember { mutableStateOf(false) }
    var showDormitorioConsumo by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showSalonConsumo = true }) {
            Text("Mostrar Consumo Salón")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showCocinaConsumo = true }) {
            Text("Mostrar Consumo Cocina")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDormitorioConsumo = true }) {
            Text("Mostrar Consumo Dormitorio")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (showSalonConsumo) {
            Text("Consumo del Salón: ${salon?.consumo() ?: 0.0}")
            Text("Atributos del Salón: ${salon?.let { "Lampara: ${it.tieneLampara}, Televisión: ${it.tieneTelevision}, Aire Acondicionado: ${it.tieneAireAcondicionado}, Encendido: ${it.encendido}" } ?: ""}")
        }

        if (showCocinaConsumo) {
            Text("Consumo de la Cocina: ${cocina?.consumo() ?: 0.0}")
            Text("Atributos de la Cocina: ${cocina?.let { "Nevera: ${it.tieneNevera}, Horno: ${it.tieneHorno}, Vitrocerámica: ${it.tieneVitroceramica}, Encendido: ${it.encendido}" } ?: ""}")
        }

        if (showDormitorioConsumo) {
            Text("Consumo del Dormitorio: ${dormitorio?.consumo() ?: 0.0}")
            Text("Atributos del Dormitorio: ${dormitorio?.let { "Altavoces: ${it.tieneAltavoces}, Lamparilla: ${it.tieneLamparilla}, Ordenador: ${it.tieneOrdenador}, Encendido: ${it.encendido}" } ?: ""}")
        }
    }
}