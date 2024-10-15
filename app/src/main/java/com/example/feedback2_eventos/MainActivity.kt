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
import com.example.feedback2_eventos.Cocina.CocinaForm
import com.example.feedback2_eventos.Cocina.CocinaRepository
import com.example.feedback2_eventos.Dormitorio.DormitorioForm
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

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    Feedback2eventosTheme {
        MainContent()
    }
}