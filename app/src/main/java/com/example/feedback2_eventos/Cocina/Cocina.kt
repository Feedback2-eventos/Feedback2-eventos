// app/src/main/java/com/example/feedback2_eventos/Cocina/Cocina.kt
package com.example.feedback2_eventos.Cocina

data class Cocina(
    val nombre: String,
    var tieneNevera: Boolean,
    var tieneHorno: Boolean,
    var tieneVitroceramica: Boolean,
    var encendido: Boolean
) {
    fun consumo(): Double {
        var consumoTotal = 0.0
        if (tieneNevera) consumoTotal += 50.0 // Ejemplo de consumo
        if (tieneHorno) consumoTotal += 100.0 // Ejemplo de consumo
        if (tieneVitroceramica) consumoTotal += 75.0 // Ejemplo de consumo
        return consumoTotal
    }
}