package com.example.feedback2_eventos.Dormitorio

import com.example.feedback2_eventos.Dispositivo

data class Dormitorio(

    val nombre: String,
    var altavoces: Dispositivo,
    var lamparilla: Dispositivo,
    var ordenador: Dispositivo,
    var encendido: Boolean

)