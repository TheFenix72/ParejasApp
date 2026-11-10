package org.proven.parejasapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(viewModel: GameViewModel = remember { GameViewModel() }) {
    val cards = viewModel.cards
    val selectedCount = viewModel.selectedCount
    val isGameOver = viewModel.isGameOver

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo1),
            contentDescription = "Fondo de pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Título
            Text(
                text = "Joc de les Parelles",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 32.dp)
            )

            // 6 cartas (3 filas x 2 columnas)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0 until 3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (col in 0 until 2) {
                            val cardIndex = row * 2 + col
                            if (cardIndex < cards.size) {
                                val card = cards[cardIndex]
                                CardItem(
                                    card = card,
                                    onClick = { viewModel.onCardClick(cardIndex) }
                                )
                            }
                        }
                    }
                }
            }

            // Botón "VALIDAR" (se habilita cuando hay 2 cartas levantadas)
            Button(
                onClick = { viewModel.onValidate() },
                enabled = selectedCount == 2,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = "VALIDAR",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Diálogo al finalizar el juego
        if (isGameOver) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = "¡Joc Finalitzat!", fontWeight = FontWeight.Bold) },
                text = { Text(text = "Has trobat totes les parelles de cartes. Enhorabona!") },
                confirmButton = {
                    TextButton(onClick = { viewModel.onReset() }) {
                        Text(text = "Torna a jugar")
                    }
                }
            )
        }
    }
}

@Composable
fun CardItem(
    card: Card,
    onClick: () -> Unit
) {
    // Si la carta está girada o ya está emparejada, se muestra la imagen; si no, el reverso.
    val imageRes = if (card.isFaceUp || card.isMatched) {
        card.imageResId
    } else {
        R.drawable.back
    }

    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 150.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = !card.isFaceUp && !card.isMatched, onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Carta",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
