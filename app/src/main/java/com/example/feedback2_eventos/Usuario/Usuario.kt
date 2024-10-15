// app/src/main/java/com/example/feedback2_eventos/Usuario/Usuario.kt
package com.example.feedback2_eventos.Usuario

import com.example.feedback2_eventos.Cocina.Cocina

data class Usuario(
    val nombre: String = "",
    val contraseña: String = "",
    val cocinas: List<Cocina> = listOf(),
    var consumo: Double = 0.0
)