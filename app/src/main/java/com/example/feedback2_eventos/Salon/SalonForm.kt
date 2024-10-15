// app/src/main/java/com/example/feedback2_eventos/Salon/SalonForm.kt
package com.example.feedback2_eventos.Salon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SalonForm(onSubmit: (Salon) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var tieneLampara by remember { mutableStateOf(false) }
    var tieneTelevision by remember { mutableStateOf(false) }
    var tieneAireAcondicionado by remember { mutableStateOf(false) }
    var encendido by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Salón") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Lámpara")
                Switch(checked = tieneLampara, onCheckedChange = { tieneLampara = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Televisión")
                Switch(checked = tieneTelevision, onCheckedChange = { tieneTelevision = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Aire Acondicionado")
                Switch(checked = tieneAireAcondicionado, onCheckedChange = { tieneAireAcondicionado = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Encendido")
                Switch(checked = encendido, onCheckedChange = { encendido = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val salon = Salon(nombre = nombre, tieneLampara = tieneLampara, tieneTelevision = tieneTelevision, tieneAireAcondicionado = tieneAireAcondicionado, encendido = encendido)
                onSubmit(salon)
            }) {
                Text("Aceptar")
            }
        }
    }
}