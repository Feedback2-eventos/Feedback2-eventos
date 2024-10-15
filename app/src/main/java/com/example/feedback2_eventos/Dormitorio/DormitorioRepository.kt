// app/src/main/java/com/example/feedback2_eventos/Dormitorio/DormitorioRepository.kt
package com.example.feedback2_eventos.Dormitorio

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.example.feedback2_eventos.Dormitorio.Dormitorio

class DormitorioRepository {
    private val db = FirebaseFirestore.getInstance()

    fun agregarDormitorio(dormitorio: Dormitorio) {
        db.collection("dormitorios")
            .document(dormitorio.nombre)
            .set(dormitorio)
            .addOnSuccessListener {
                Log.d("DormitorioRepository", "Dormitorio agregado exitosamente: ${dormitorio.nombre}")
            }
            .addOnFailureListener { e ->
                Log.e("DormitorioRepository", "Error al agregar el dormitorio: ${dormitorio.nombre}", e)
            }
    }

    fun obtenerDormitorio(nombreDormitorio: String, callback: (Dormitorio?) -> Unit) {
        db.collection("dormitorios")
            .document(nombreDormitorio)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val dormitorio = document.toObject(Dormitorio::class.java)
                    callback(dormitorio)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("DormitorioRepository", "Error al obtener el dormitorio: $nombreDormitorio", e)
                callback(null)
            }
    }
}