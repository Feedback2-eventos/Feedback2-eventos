// app/src/main/java/com/example/feedback2_eventos/MainActivity.kt
package com.example.feedback2_eventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    var showMenuScreen by remember { mutableStateOf(false) }
    var salon by remember { mutableStateOf<Salon?>(null) }
    var cocina by remember { mutableStateOf<Cocina?>(null) }
    var dormitorio by remember { mutableStateOf<Dormitorio?>(null) }

    if (showMenuScreen) {
        MenuScreen(
            salon = salon,
            cocina = cocina,
            dormitorio = dormitorio,
            onShowSalonConsumo = { /* Implementar lógica para mostrar consumo del salón */ },
            onShowCocinaConsumo = { /* Implementar lógica para mostrar consumo de la cocina */ },
            onShowDormitorioConsumo = { /* Implementar lógica para mostrar consumo del dormitorio */ },
            onBack = { showMenuScreen = false }
        )
    } else {
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
            Button(onClick = { showMenuScreen = true }) {
                Text("Ir al MenuScreen")
            }

            if (showSalonForm) {
                SalonForm(onSubmit = { newSalon ->
                    val salonRepository = SalonRepository()
                    salonRepository.agregarSalon(newSalon)
                    salon = newSalon
                    showSalonForm = false
                    showMenuScreen = true
                })
            }

            if (showCocinaForm) {
                CocinaForm(onSubmit = { newCocina ->
                    val cocinaRepository = CocinaRepository()
                    cocinaRepository.agregarCocina(newCocina)
                    cocina = newCocina
                    showCocinaForm = false
                    showMenuScreen = true
                })
            }

            if (showDormitorioForm) {
                DormitorioForm(onSubmit = { newDormitorio ->
                    val dormitorioRepository = DormitorioRepository()
                    dormitorioRepository.agregarDormitorio(newDormitorio)
                    dormitorio = newDormitorio
                    showDormitorioForm = false
                    showMenuScreen = true
                })
            }
        }
    }
}