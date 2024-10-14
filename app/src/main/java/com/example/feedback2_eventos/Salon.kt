// app/src/main/java/com/example/feedback2_eventos/Salon.kt
package com.example.feedback2_eventos

data class Salon(

    val nombre: String,
    var lampara: Dispositivo,
    var television: Dispositivo,
    var aireAcondicionado: Dispositivo,
    var encendido: Boolean

)