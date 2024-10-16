package com.example.feedback2_eventos.Usuario

import android.os.Handler
import android.os.Looper
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Salon.Salon
import com.example.feedback2_eventos.Dormitorio.Dormitorio
import com.example.feedback2_eventos.MainActivity

class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 10000 // Check every 5 seconds

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

    fun agregarDormitorioAUsuario(username: String, dormitorio: Dormitorio) {
        db.collection("usuarios")
            .document(username)
            .get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject(Usuario::class.java)
                usuario?.let {
                    it.dormitorios.add(dormitorio)
                    it.actualizarConsumoTotal()
                    db.collection("usuarios").document(username).set(it)
                }
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al agregar dormitorio al usuario: $username", e)
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

    fun actualizarConsumoDormitorio(username: String, dormitorioConsumo: Double) {
        db.collection("usuarios")
            .document(username)
            .update("dormitorioConsumo", dormitorioConsumo)
            .addOnSuccessListener {
                Log.d("UsuarioRepository", "Consumo del dormitorio actualizado para el usuario: $username")
            }
            .addOnFailureListener { e ->
                Log.e("UsuarioRepository", "Error al actualizar el consumo del dormitorio del usuario: $username", e)
            }
    }
}