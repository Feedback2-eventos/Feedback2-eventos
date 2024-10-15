package com.example.feedback2_eventos.Usuario

import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Salon.Salon

class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()

    fun obtenerUsuario(username: String, callback: (Usuario?) -> Unit) {
        db.collection("usuarios")
            .document(username)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val usuario = document.toObject(Usuario::class.java)
                    callback(usuario)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al obtener el usuario: $username", e)
                callback(null)
            }
    }

    fun agregarCocinaAUsuario(username: String, cocina: Cocina) {
        db.collection("usuarios")
            .document(username)
            .get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject(Usuario::class.java)
                usuario?.let {
                    it.cocinas.add(cocina)
                    it.actualizarConsumoTotal()
                    db.collection("usuarios").document(username).set(it)
                }
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al agregar cocina al usuario: $username", e)
            }
    }

    fun agregarSalonAUsuario(username: String, salon: Salon) {
        db.collection("usuarios")
            .document(username)
            .get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject(Usuario::class.java)
                usuario?.let {
                    if (it.salones.isEmpty()) {
                        it.salones.add(salon)
                        it.actualizarConsumoTotal()
                        db.collection("usuarios").document(username).set(it)
                    } else {
                        Log.e("UsuarioRepository", "El usuario ya tiene un salón: $username")
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al agregar salón al usuario: $username", e)
            }
    }

    fun actualizarConsumoUsuario(username: String, consumo: Double) {
        db.collection("usuarios")
            .document(username)
            .update("consumo", consumo)
            .addOnSuccessListener {
                Log.d("UsuarioRepository", "Consumo actualizado para el usuario: $username")
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al actualizar el consumo del usuario: $username", e)
            }
    }

    fun actualizarConsumoSalon(username: String, salonConsumo: Double) {
        db.collection("usuarios")
            .document(username)
            .update("salonConsumo", salonConsumo)
            .addOnSuccessListener {
                Log.d("UsuarioRepository", "Consumo del salón actualizado para el usuario: $username")
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al actualizar el consumo del salón del usuario: $username", e)
            }
    }
}