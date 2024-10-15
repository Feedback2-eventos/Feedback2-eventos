// app/src/main/java/com/example/feedback2_eventos/Salon/SalonForm.kt
package com.example.feedback2_eventos.Salon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Dispositivo

@Composable
fun SalonForm(onSubmit: (Salon) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var lamparaNombre by remember { mutableStateOf("") }
    var lamparaConsumo by remember { mutableStateOf("") }
    var televisionNombre by remember { mutableStateOf("") }
    var televisionConsumo by remember { mutableStateOf("") }
    var aireNombre by remember { mutableStateOf("") }
    var aireConsumo by remember { mutableStateOf("") }
    var encendido by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del Salón") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = lamparaNombre, onValueChange = { lamparaNombre = it }, label = { Text("Nombre de la Lámpara") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = lamparaConsumo, onValueChange = { lamparaConsumo = it }, label = { Text("Consumo de la Lámpara") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = televisionNombre, onValueChange = { televisionNombre = it }, label = { Text("Nombre de la Televisión") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = televisionConsumo, onValueChange = { televisionConsumo = it }, label = { Text("Consumo de la Televisión") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = aireNombre, onValueChange = { aireNombre = it }, label = { Text("Nombre del Aire Acondicionado") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = aireConsumo, onValueChange = { aireConsumo = it }, label = { Text("Consumo del Aire Acondicionado") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Encendido")
                Switch(checked = encendido, onCheckedChange = { encendido = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val lampara = Dispositivo(nombre = lamparaNombre, consumo = lamparaConsumo.toDouble(), encendido = false)
                val television = Dispositivo(nombre = televisionNombre, consumo = televisionConsumo.toDouble(), encendido = false)
                val aireAcondicionado = Dispositivo(nombre = aireNombre, consumo = aireConsumo.toDouble(), encendido = false)
                val salon = Salon(nombre = nombre, lampara = lampara, television = television, aireAcondicionado = aireAcondicionado, encendido = encendido)
                onSubmit(salon)
            }) {
                Text("Aceptar")
            }
        }
    }
}