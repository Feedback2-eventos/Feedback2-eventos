package com.example.feedback2_eventos.Salon

import android.os.AsyncTask

data class Salon(
    val nombre: String = "",
    var tieneTelevision: Boolean = false,
    var tieneLampara: Boolean = false,
    var tieneAireAcondicionado: Boolean = false,
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
        if (tieneTelevision) incremento += 30.0
        if (tieneLampara) incremento += 10.0
        if (tieneAireAcondicionado) incremento += 150.0
        return incremento
    }
}