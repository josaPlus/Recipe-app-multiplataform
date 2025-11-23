package org.lasalle.recipeapp.ui.screeens.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun LoadingOverlay() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize() // 1. Ocupar toda la pantalla
            .background(Color.Black.copy(alpha = 0.7f)) // 2. Fondo Negro al 70% de opacidad (ajusta el 0.7f si quieres ver más o menos)
            .clickable(enabled = false) {} // 3. Bloquear clicks a la pantalla de atrás
            .zIndex(10f), // 4. Asegurar que esté hasta arriba (opcional si usas el Box bien)
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .graphicsLayer(rotationZ = rotation),
                tint = Color.White // Forzamos blanco para que contraste con el negro
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cocinando...",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White, // Texto blanco
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun LoadingOverlayPreview() {
    LoadingOverlay()
}