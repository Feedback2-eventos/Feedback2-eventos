package com.example.feedback2_eventos

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde energía
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedWidth by infiniteTransition.animateFloat(
        initialValue = 200f,
        targetValue = 220f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier.width(animatedWidth.dp).height(50.dp),
        colors = colors,
        shape = CircleShape
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}