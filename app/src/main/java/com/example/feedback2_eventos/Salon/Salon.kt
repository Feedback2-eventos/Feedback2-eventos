// app/src/main/java/com/example/feedback2_eventos/Salon/Salon.kt
package com.example.feedback2_eventos.Salon

data class Salon(
    val nombre: String,
    var tieneLampara: Boolean,
    var tieneTelevision: Boolean,
    var tieneAireAcondicionado: Boolean,
    var encendido: Boolean,
    var valorPeligroso: Double = 0.0
) {
    fun consumo(): Double {
        var consumoTotal = 0.0
        if (tieneLampara) consumoTotal += 10.0
        if (tieneTelevision) consumoTotal += 20.0
        if (tieneAireAcondicionado) consumoTotal += 30.0
        return consumoTotal
    }
}