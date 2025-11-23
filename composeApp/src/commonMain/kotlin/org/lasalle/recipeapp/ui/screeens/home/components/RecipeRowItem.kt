package org.lasalle.recipeapp.ui.screeens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.lasalle.recipeapp.models.Recipe

@Composable
fun RecipeRowItem(
    recipe: Recipe,
    onClick: () -> Unit
) {
    // Definimos el color naranja del diseño
    val orangeColor = Color(0xFFE67C24)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp) // Altura fija para consistencia
            .padding(bottom = 12.dp), // Separación entre tarjetas
        shape = RoundedCornerShape(20.dp), // Bordes muy redondeados
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Fondo blanco
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Sombra suave opcional
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. IMAGEN CIRCULAR
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp) // Tamaño de la imagen
                    .clip(CircleShape), // Recorte circular
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 2. TEXTOS (Título y Categoría)
            Column(
                modifier = Modifier.weight(1f), // Ocupa todo el espacio disponible en medio
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    maxLines = 1, // Solo 1 línea
                    overflow = TextOverflow.Ellipsis // Pone "..." si es muy largo
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = recipe.category,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                )
            }

            // 3. TIEMPO (Icono y Minutos)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule, // Usa Outlined para que sea finito
                    contentDescription = null,
                    tint = orangeColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${recipe.minutes} min",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = orangeColor,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}