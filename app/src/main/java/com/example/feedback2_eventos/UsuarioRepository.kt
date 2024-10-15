// app/src/main/java/com/example/feedback2_eventos/Usuario/UsuarioRepository.kt
package com.example.feedback2_eventos.Usuario

import com.example.feedback2_eventos.Cocina.Cocina
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()

    fun agregarUsuario(usuario: Usuario) {
        db.collection("usuarios")
            .document(usuario.nombre)
            .set(usuario)
            .addOnSuccessListener {
                Log.d("UsuarioRepository", "Usuario agregado exitosamente: ${usuario.nombre}")
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al agregar el usuario: ${usuario.nombre}", e)
            }
    }

    fun obtenerUsuario(nombreUsuario: String, callback: (Usuario?) -> Unit) {
        db.collection("usuarios")
            .document(nombreUsuario)
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
                Log.e("UsuarioRepository", "Error al obtener el usuario: $nombreUsuario", e)
                callback(null)
            }
    }

    fun agregarCocinaAUsuario(nombreUsuario: String, cocina: Cocina) {
        val usuarioRef = db.collection("usuarios").document(nombreUsuario)
        usuarioRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val usuario = document.toObject(Usuario::class.java)
                val cocinasActualizadas = usuario?.cocinas?.toMutableList() ?: mutableListOf()
                cocinasActualizadas.add(cocina)
                usuarioRef.update("cocinas", cocinasActualizadas)
                    .addOnSuccessListener {
                        Log.d("UsuarioRepository", "Cocina agregada exitosamente al usuario: $nombreUsuario")
                    }
                    .addOnFailureListener { e ->
                        Log.e("UsuarioRepository", "Error al agregar la cocina al usuario: $nombreUsuario", e)
                    }
            } else {
                Log.e("UsuarioRepository", "Usuario no encontrado: $nombreUsuario")
            }
        }.addOnFailureListener { e ->
            Log.e("UsuarioRepository", "Error al obtener el usuario: $nombreUsuario", e)
        }
    }
}