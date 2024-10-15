// app/src/main/java/com/example/feedback2_eventos/StartScreen.kt
package com.example.feedback2_eventos

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feedback2_eventos.Usuario.Usuario
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun StartScreen(onStart: (Boolean, String) -> Unit) {
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
                val newUser = Usuario(nombre = username, contraseña = password)
                db.collection("usuarios")
                    .document(username)
                    .set(newUser)
                    .addOnSuccessListener {
                        loading = false
                        onStart(true, username)
                    }
                    .addOnFailureListener { e ->
                        loading = false
                        errorMessage = e.message
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Usuario")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                loading = true
                errorMessage = null
                db.collection("usuarios")
                    .whereEqualTo("nombre", username)
                    .whereEqualTo("contraseña", password)
                    .get()
                    .addOnSuccessListener { documents ->
                        loading = false
                        if (!documents.isEmpty) {
                            val user = documents.documents.first().toObject(Usuario::class.java)
                            val hasCocinas = user?.cocinas?.isNotEmpty() == true
                            onStart(hasCocinas, username)
                        } else {
                            errorMessage = "Usuario o contraseña incorrectos"
                        }
                    }
                    .addOnFailureListener { e ->
                        loading = false
                        errorMessage = e.message
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Acceder")
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