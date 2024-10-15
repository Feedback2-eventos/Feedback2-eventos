package com.example.feedback2_eventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Salon.SalonRepository
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Cocina.CocinaRepository
import com.example.feedback2_eventos.ui.theme.Feedback2eventosTheme
import com.example.feedback2_eventos.AnimatedScalableButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback2eventosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.fondo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        MainContent(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var showSalonForm by remember { mutableStateOf(false) }
    var showCocinaForm by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(20.dp)) {
        AnimatedScalableButton(text = "Añadir Salón") {
            showSalonForm = true
        }
        Spacer(modifier = Modifier.height(8.dp))
        AnimatedScalableButton(text = "Añadir Cocina") {
            showCocinaForm = true
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

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    Feedback2eventosTheme {
        MainContent()
    }
}