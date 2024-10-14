package com.example.feedback2_eventos

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class SalonRepository {
    private val db = FirebaseFirestore.getInstance()

    fun agregarSalon(salon: Salon) {
        db.collection("salones")
            .document(salon.nombre)
            .set(salon)
            .addOnSuccessListener {
                Log.d("SalonRepository", "Salón agregado exitosamente: ${salon.nombre}")
                // Aquí puedes agregar cualquier lógica adicional que necesites
            }
            .addOnFailureListener { e ->
                Log.e("SalonRepository", "Error al agregar el salón: ${salon.nombre}", e)
                // Aquí puedes manejar el error, por ejemplo, mostrando un mensaje al usuario
            }
    }

    fun obtenerSalon(nombreSalon: String, callback: (Salon?) -> Unit) {
        db.collection("salones")
            .document(nombreSalon)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val salon = document.toObject(Salon::class.java)
                    callback(salon)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("SalonRepository", "Error al obtener el salón: $nombreSalon", e)
                callback(null)
            }
    }
}