// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Salon.SalonRepository
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Cocina.CocinaRepository
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.Dormitorio.DormitorioRepository
import com.example.feedback2_eventos.ui.theme.Feedback2eventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback2eventosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var showSalonForm by remember { mutableStateOf(false) }
    var showCocinaForm by remember { mutableStateOf(false) }
    var showDormitorioForm by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = { showSalonForm = true }) {
            Text("Añadir Salón")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showCocinaForm = true }) {
            Text("Añadir Cocina")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { showDormitorioForm = true }) {
            Text("Añadir Dormitorio")
        }

        if (showSalonForm) {
            SalonForm(onSubmit = { salon ->
                val salonRepository = SalonRepository()
                salonRepository.agregarSalon(salon)
                showSalonForm = false
            })
        }

        if (showCocinaForm) {
            CocinaForm(onSubmit = { cocina ->
                val cocinaRepository = CocinaRepository()
                cocinaRepository.agregarCocina(cocina)
                showCocinaForm = false
            })
        }

        if (showDormitorioForm) {
            DormitorioForm(onSubmit = { dormitorio ->
                val dormitorioRepository = DormitorioRepository()
                dormitorioRepository.agregarDormitorio(dormitorio)
                showDormitorioForm = false
            })
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    Feedback2eventosTheme {
        MainContent()
    }
}