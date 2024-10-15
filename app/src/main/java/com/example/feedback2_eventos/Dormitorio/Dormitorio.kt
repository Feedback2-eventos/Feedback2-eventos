// app/src/main/java/com/example/feedback2_eventos/Dormitorio/Dormitorio.kt
package com.example.feedback2_eventos.Dormitorio

data class Dormitorio(
    val nombre: String,
    var tieneAltavoces: Boolean,
    var tieneLamparilla: Boolean,
    var tieneOrdenador: Boolean,
    var encendido: Boolean
) {
    fun consumo(): Double {
        var consumoTotal = 0.0
        if (tieneAltavoces) consumoTotal += 15.0 // Ejemplo de consumo
        if (tieneLamparilla) consumoTotal += 5.0 // Ejemplo de consumo
        if (tieneOrdenador) consumoTotal += 60.0 // Ejemplo de consumo
        return consumoTotal
    }
}