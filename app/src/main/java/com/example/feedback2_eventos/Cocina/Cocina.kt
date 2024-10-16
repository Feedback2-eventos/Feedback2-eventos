package com.example.feedback2_eventos.Cocina

import android.os.AsyncTask

data class Cocina(
    val nombre: String = "",
    var tieneNevera: Boolean = false,
    var tieneHorno: Boolean = false,
    var tieneVitroceramica: Boolean = false,
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
        if (tieneNevera) incremento += 50.0
        if (tieneHorno) incremento += 100.0
        if (tieneVitroceramica) incremento += 75.0
        return incremento
    }
}