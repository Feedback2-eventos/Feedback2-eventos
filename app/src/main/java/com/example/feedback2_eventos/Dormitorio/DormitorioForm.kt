// app/src/main/java/com/example/feedback2_eventos/Dormitorio/DormitorioForm.kt
package com.example.feedback2_eventos.Dormitorio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Dispositivo

@Composable
fun DormitorioForm(onSubmit: (Dormitorio) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var altavocesNombre by remember { mutableStateOf("") }
    var altavocesConsumo by remember { mutableStateOf("") }
    var lamparillaNombre by remember { mutableStateOf("") }
    var lamparillaConsumo by remember { mutableStateOf("") }
    var ordenadorNombre by remember { mutableStateOf("") }
    var ordenadorConsumo by remember { mutableStateOf("") }
    var encendido by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Dormitorio") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = altavocesNombre, onValueChange = { altavocesNombre = it }, label = { Text("Nombre de los Altavoces") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = altavocesConsumo, onValueChange = { altavocesConsumo = it }, label = { Text("Consumo de los Altavoces") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = lamparillaNombre, onValueChange = { lamparillaNombre = it }, label = { Text("Nombre de la Lamparilla") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = lamparillaConsumo, onValueChange = { lamparillaConsumo = it }, label = { Text("Consumo de la Lamparilla") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = ordenadorNombre, onValueChange = { ordenadorNombre = it }, label = { Text("Nombre del Ordenador") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = ordenadorConsumo, onValueChange = { ordenadorConsumo = it }, label = { Text("Consumo del Ordenador") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Encendido")
                Switch(checked = encendido, onCheckedChange = { encendido = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val altavoces = Dispositivo(nombre = altavocesNombre, consumo = altavocesConsumo.toDouble(), encendido = false)
                val lamparilla = Dispositivo(nombre = lamparillaNombre, consumo = lamparillaConsumo.toDouble(), encendido = false)
                val ordenador = Dispositivo(nombre = ordenadorNombre, consumo = ordenadorConsumo.toDouble(), encendido = false)
                val dormitorio = Dormitorio(nombre = nombre, altavoces = altavoces, lamparilla = lamparilla, ordenador = ordenador, encendido = encendido)
                onSubmit(dormitorio)
            }) {
                Text("Aceptar")
            }
        }
    }
}