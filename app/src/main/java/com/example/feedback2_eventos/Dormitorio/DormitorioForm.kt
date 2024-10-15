// app/src/main/java/com/example/feedback2_eventos/Dormitorio/DormitorioForm.kt
package com.example.feedback2_eventos.Dormitorio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DormitorioForm(onSubmit: (Dormitorio) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var tieneAltavoces by remember { mutableStateOf(false) }
    var tieneLamparilla by remember { mutableStateOf(false) }
    var tieneOrdenador by remember { mutableStateOf(false) }
    var encendido by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Dormitorio") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Altavoces")
                Switch(checked = tieneAltavoces, onCheckedChange = { tieneAltavoces = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Lamparilla")
                Switch(checked = tieneLamparilla, onCheckedChange = { tieneLamparilla = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Ordenador")
                Switch(checked = tieneOrdenador, onCheckedChange = { tieneOrdenador = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Encendido")
                Switch(checked = encendido, onCheckedChange = { encendido = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val dormitorio = Dormitorio(nombre = nombre, tieneAltavoces = tieneAltavoces, tieneLamparilla = tieneLamparilla, tieneOrdenador = tieneOrdenador, encendido = encendido)
                onSubmit(dormitorio)
            }) {
                Text("Aceptar")
            }
        }
    }
}