// app/src/main/java/com/example/feedback2_eventos/Dormitorio/Dormitorio.kt
package com.example.feedback2_eventos.Dormitorio

import android.os.AsyncTask

data class Dormitorio(
    val nombre: String = "",
    var tieneAltavoces: Boolean = false,
    var tieneLamparilla: Boolean = false,
    var tieneOrdenador: Boolean = false,
    var encendido: Boolean = false
) {
    private var consumoTotal = 0.0
    private var consumoTask: ConsumoTask? = null

    init {
        if (encendido) startConsumo()
    }

    fun consumo(): Double {
        return consumoTotal
    }

    fun updateEncendido(value: Boolean) {
        encendido = value
        if (encendido) {
            startConsumo()
        } else {
            stopConsumo()
        }
    }

    private fun startConsumo() {
        consumoTask = ConsumoTask()
        consumoTask?.execute()
    }

    private fun stopConsumo() {
        consumoTask?.cancel(true)
    }

    private inner class ConsumoTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                Thread.sleep(2000)
                consumoTotal += calculateConsumoIncrement()
            }
            return null
        }
    }

    fun calculateConsumoIncrement(): Double {
        var incremento = 0.0
        if (tieneAltavoces) incremento += 15.0
        if (tieneLamparilla) incremento += 5.0
        if (tieneOrdenador) incremento += 60.0
        return incremento
    }
}