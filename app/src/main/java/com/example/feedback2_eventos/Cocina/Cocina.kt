package com.example.feedback2_eventos.Cocina

import com.example.feedback2_eventos.Dispositivo

data class Cocina(

    val nombre: String,
    var nevera: Dispositivo,
    var horno: Dispositivo,
    var vitroceramica: Dispositivo,
    var encendido: Boolean

)