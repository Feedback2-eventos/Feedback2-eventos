// app/src/main/java/com/example/feedback2_eventos/ConsumoPeligrosoScreen.kt
package com.example.feedback2_eventos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Dormitorio.Dormitorio

@Composable
fun ConsumoPeligrosoScreen(
    salon: Salon?,
    cocina: Cocina?,
    dormitorio: Dormitorio?,
    onSave: (Double, Double, Double) -> Unit,
    onBack: () -> Unit
) {
    var salonPeligroso by remember { mutableStateOf(salon?.valorPeligroso ?: 0.0) }
    var cocinaPeligroso by remember { mutableStateOf(cocina?.valorPeligroso ?: 0.0) }
    var dormitorioPeligroso by remember { mutableStateOf(dormitorio?.valorPeligroso ?: 0.0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = salonPeligroso.toString(),
            onValueChange = { salonPeligroso = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Valor Peligroso Salón") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cocinaPeligroso.toString(),
            onValueChange = { cocinaPeligroso = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Valor Peligroso Cocina") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = dormitorioPeligroso.toString(),
            onValueChange = { dormitorioPeligroso = it.toDoubleOrNull() ?: 0.0 },
            label = { Text("Valor Peligroso Dormitorio") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onSave(salonPeligroso, cocinaPeligroso, dormitorioPeligroso) }) {
            Text("Guardar")
        }
    }
}