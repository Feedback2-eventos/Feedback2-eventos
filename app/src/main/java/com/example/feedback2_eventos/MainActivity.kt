// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Salon.SalonForm
import com.example.feedback2_eventos.Salon.SalonRepository
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Cocina.CocinaForm
import com.example.feedback2_eventos.Cocina.CocinaRepository
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.DormitorioForm
import com.example.feedback2_eventos.Dormitorio.DormitorioRepository
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.ui.theme.Feedback2eventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback2eventosTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var showSalonForm by remember { mutableStateOf(false) }
    var showCocinaForm by remember { mutableStateOf(false) }
    var showDormitorioForm by remember { mutableStateOf(false) }
    var salonConsumo by remember { mutableStateOf(0.0) }
    var cocinaConsumo by remember { mutableStateOf(0.0) }
    var dormitorioConsumo by remember { mutableStateOf(0.0) }
    var salonAttributes by remember { mutableStateOf("") }
    var cocinaAttributes by remember { mutableStateOf("") }
    var dormitorioAttributes by remember { mutableStateOf("") }
    var salon by remember { mutableStateOf<Salon?>(null) }
    var cocina by remember { mutableStateOf<Cocina?>(null) }
    var dormitorio by remember { mutableStateOf<Dormitorio?>(null) }

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
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            salon?.let {
                salonConsumo = it.consumo()
                salonAttributes = "Lampara: ${it.tieneLampara}, Televisión: ${it.tieneTelevision}, Aire Acondicionado: ${it.tieneAireAcondicionado}, Encendido: ${it.encendido}"
            }
        }) {
            Text("Mostrar Consumo Salón")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            cocina?.let {
                cocinaConsumo = it.consumo()
                cocinaAttributes = "Nevera: ${it.tieneNevera}, Horno: ${it.tieneHorno}, Vitrocerámica: ${it.tieneVitroceramica}, Encendido: ${it.encendido}"
            }
        }) {
            Text("Mostrar Consumo Cocina")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            dormitorio?.let {
                dormitorioConsumo = it.consumo()
                dormitorioAttributes = "Altavoces: ${it.tieneAltavoces}, Lamparilla: ${it.tieneLamparilla}, Ordenador: ${it.tieneOrdenador}, Encendido: ${it.encendido}"
            }
        }) {
            Text("Mostrar Consumo Dormitorio")
        }
        Text("Consumo del Salón: $salonConsumo")
        Text("Atributos del Salón: $salonAttributes")
        Text("Consumo de la Cocina: $cocinaConsumo")
        Text("Atributos de la Cocina: $cocinaAttributes")
        Text("Consumo del Dormitorio: $dormitorioConsumo")
        Text("Atributos del Dormitorio: $dormitorioAttributes")

        if (showSalonForm) {
            SalonForm(onSubmit = { newSalon ->
                val salonRepository = SalonRepository()
                salonRepository.agregarSalon(newSalon)
                salon = newSalon
                showSalonForm = false
            })
        }

        if (showCocinaForm) {
            CocinaForm(onSubmit = { newCocina ->
                val cocinaRepository = CocinaRepository()
                cocinaRepository.agregarCocina(newCocina)
                cocina = newCocina
                showCocinaForm = false
            })
        }

        if (showDormitorioForm) {
            DormitorioForm(onSubmit = { newDormitorio ->
                val dormitorioRepository = DormitorioRepository()
                dormitorioRepository.agregarDormitorio(newDormitorio)
                dormitorio = newDormitorio
                showDormitorioForm = false
            })
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