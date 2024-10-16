package com.example.feedback2_eventos.Menus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Cocina.Cocina
import com.example.feedback2_eventos.Usuario.Usuario
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun StartScreen(onStart: (Boolean, String, Cocina?) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loading = true
                errorMessage = null
                checkUserCocinas(db, username, password, onStart) { error ->
                    errorMessage = error
                    loading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Acceder")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                loading = true
                errorMessage = null
                checkIfUserExists(db, username) { exists ->
                    if (exists) {
                        errorMessage = "El nombre de usuario ya existe"
                        loading = false
                    } else {
                        val newUser = Usuario(nombre = username, contraseña = password)
                        db.collection("usuarios").document(username).set(newUser)
                            .addOnSuccessListener {
                                onStart(false, username, null)
                                loading = false
                            }
                            .addOnFailureListener { e ->
                                errorMessage = "Error al registrar: ${e.message}"
                                loading = false
                            }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
        if (loading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

private fun checkIfUserExists(db: FirebaseFirestore, username: String, callback: (Boolean) -> Unit) {
    db.collection("usuarios")
        .document(username)
        .get()
        .addOnSuccessListener { document ->
            callback(document.exists())
        }
        .addOnFailureListener { e ->
            // Handle error if needed
            callback(false)
        }
}

private fun checkUserCocinas(
    db: FirebaseFirestore,
    username: String,
    password: String,
    onStart: (Boolean, String, Cocina?) -> Unit,
    onError: (String) -> Unit
) {
    db.collection("usuarios")
        .document(username)
        .get()
        .addOnSuccessListener { document ->
            val user = document.toObject(Usuario::class.java)
            if (user != null && user.contraseña == password) {
                val hasCocinas = user.cocinas.isNotEmpty()
                val cocina = if (hasCocinas) user.cocinas[0] else null
                onStart(hasCocinas, username, cocina)
            } else {
                onError("Nombre de usuario o contraseña incorrectos")
            }
        }
        .addOnFailureListener { e ->
            onError("Error al acceder: ${e.message}")
        }
}