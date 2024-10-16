package com.example.feedback2_eventos.Menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.AnimatedButton
import com.example.feedback2_eventos.Cocina.Cocina
import com.google.firebase.firestore.FirebaseFirestore
import com.example.feedback2_eventos.R

@Composable
fun StartScreen(onStart: (Boolean, String, Cocina?) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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

            AnimatedButton(
                text = "Acceder",
                onClick = {
                    loading = true
                    checkIfUserExists(db, username) { exists ->
                        if (exists) {
                            checkUserCocinas(db, username) { hasCocinas, cocina ->
                                onStart(hasCocinas, username, cocina)
                                loading = false
                            }
                        } else {
                            errorMessage = "Usuario no encontrado"
                            loading = false
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
            )

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedButton(
                text = "Registrar",
                onClick = {
                    // Lógica para registrar un nuevo usuario
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
            )

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
}

fun checkUserCocinas(db: FirebaseFirestore, username: String, callback: (Boolean, Cocina?) -> Unit) {
    db.collection("cocinas").document(username).get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val cocina = document.toObject(Cocina::class.java)
                callback(true, cocina)
            } else {
                callback(false, null)
            }
        }
        .addOnFailureListener {
            callback(false, null)
        }
}

fun checkIfUserExists(db: FirebaseFirestore, username: String, callback: (Boolean) -> Unit) {
    db.collection("usuarios").document(username).get()
        .addOnSuccessListener { document ->
            callback(document != null && document.exists())
        }
        .addOnFailureListener {
            callback(false)
        }
}