// app/src/main/java/com/example/feedback2_eventos/Usuario/Usuario.kt
package com.example.feedback2_eventos.Usuario

import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Dormitorio.Dormitorio

data class Usuario(
    var nombre: String = "",
    var contraseña: String = "",
    var cocinas: MutableList<Cocina> = mutableListOf(),
    var salones: MutableList<Salon> = mutableListOf(),
    var dormitorios: MutableList<Dormitorio> = mutableListOf(),
    var consumo: Double = 0.0
) {
    constructor() : this("", "", mutableListOf(), mutableListOf(), mutableListOf(), 0.0)

    fun actualizarConsumoTotal() {
        consumo = cocinas.sumOf { it.consumo() } + salones.sumOf { it.consumo() } + dormitorios.sumOf { it.consumo() }
    }
}