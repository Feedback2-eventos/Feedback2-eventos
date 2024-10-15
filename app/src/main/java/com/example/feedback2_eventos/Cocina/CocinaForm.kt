package com.example.feedback2_eventos.Cocina

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Usuario.UsuarioRepository

@Composable
fun CocinaForm(username: String, onSubmit: (Cocina) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var tieneNevera by remember { mutableStateOf(false) }
    var tieneHorno by remember { mutableStateOf(false) }
    var tieneVitroceramica by remember { mutableStateOf(false) }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre de la Cocina") })
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Nevera")
                Switch(checked = tieneNevera, onCheckedChange = { tieneNevera = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Horno")
                Switch(checked = tieneHorno, onCheckedChange = { tieneHorno = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Tiene Vitrocerámica")
                Switch(checked = tieneVitroceramica, onCheckedChange = { tieneVitroceramica = it })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val cocina = Cocina(nombre = nombre, tieneNevera = tieneNevera, tieneHorno = tieneHorno, tieneVitroceramica = tieneVitroceramica, encendido = false)
                val usuarioRepository = UsuarioRepository()
                usuarioRepository.agregarCocinaAUsuario(username, cocina)
                onSubmit(cocina)
            }) {
                Text("Aceptar")
            }
        }
    }
}