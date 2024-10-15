// app/src/main/java/com/example/feedback2_eventos/Salon/Salon.kt
package com.example.feedback2_eventos.Salon

data class Salon(
    val nombre: String,
    var tieneLampara: Boolean,
    var tieneTelevision: Boolean,
    var tieneAireAcondicionado: Boolean,
    var encendido: Boolean
) {
    fun consumo(): Double {
        var consumoTotal = 0.0
        if (tieneLampara) consumoTotal += 10.0 // Ejemplo de consumo
        if (tieneTelevision) consumoTotal += 20.0 // Ejemplo de consumo
        if (tieneAireAcondicionado) consumoTotal += 30.0 // Ejemplo de consumo
        return consumoTotal
    }
}