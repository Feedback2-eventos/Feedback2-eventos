// app/src/main/java/com/example/feedback2_eventos/Cocina/CocinaForm.kt
package com.example.feedback2_eventos.Cocina

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Dispositivo

@Composable
fun CocinaForm(onSubmit: (Cocina) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var neveraNombre by remember { mutableStateOf("") }
    var neveraConsumo by remember { mutableStateOf("") }
    var hornoNombre by remember { mutableStateOf("") }
    var hornoConsumo by remember { mutableStateOf("") }
    var vitroceramicaNombre by remember { mutableStateOf("") }
    var vitroceramicaConsumo by remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre de la Cocina") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = neveraNombre, onValueChange = { neveraNombre = it }, label = { Text("Nombre de la Nevera") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = neveraConsumo, onValueChange = { neveraConsumo = it }, label = { Text("Consumo de la Nevera") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = hornoNombre, onValueChange = { hornoNombre = it }, label = { Text("Nombre del Horno") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = hornoConsumo, onValueChange = { hornoConsumo = it }, label = { Text("Consumo del Horno") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = vitroceramicaNombre, onValueChange = { vitroceramicaNombre = it }, label = { Text("Nombre de la Vitrocerámica") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = vitroceramicaConsumo, onValueChange = { vitroceramicaConsumo = it }, label = { Text("Consumo de la Vitrocerámica") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val nevera = Dispositivo(nombre = neveraNombre, consumo = neveraConsumo.toDouble(), encendido = false)
                val horno = Dispositivo(nombre = hornoNombre, consumo = hornoConsumo.toDouble(), encendido = false)
                val vitroceramica = Dispositivo(nombre = vitroceramicaNombre, consumo = vitroceramicaConsumo.toDouble(), encendido = false)
                val cocina = Cocina(nombre = nombre, nevera = nevera, horno = horno, vitroceramica = vitroceramica, encendido = false)
                onSubmit(cocina)
            }) {
                Text("Aceptar")
            }
        }
    }
}