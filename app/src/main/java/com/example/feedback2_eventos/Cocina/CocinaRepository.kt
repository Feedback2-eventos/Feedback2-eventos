// app/src/main/java/com/example/feedback2_eventos/Cocina/CocinaRepository.kt
package com.example.feedback2_eventos.Cocina

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class CocinaRepository {
    private val db = FirebaseFirestore.getInstance()

    fun agregarCocina(cocina: Cocina) {
        db.collection("cocinas")
            .document(cocina.nombre)
            .set(cocina)
            .addOnSuccessListener {
                Log.d("CocinaRepository", "Cocina agregada exitosamente: ${cocina.nombre}")
            }
            .addOnFailureListener { e ->
                Log.e("CocinaRepository", "Error al agregar la cocina: ${cocina.nombre}", e)
            }
    }

    fun obtenerCocina(nombreCocina: String, callback: (Cocina?) -> Unit) {
        db.collection("cocinas")
            .document(nombreCocina)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val cocina = document.toObject(Cocina::class.java)
                    callback(cocina)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("CocinaRepository", "Error al obtener la cocina: $nombreCocina", e)
                callback(null)
            }
    }
}