package com.example.playland2.feature.snake.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playland2.feature.snake.presentation.SnakeViewModel

@Composable
fun SnakeScreen(
    onBack: () -> Unit,
    viewModel: SnakeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Snake", 
            style = MaterialTheme.typography.headlineMedium, 
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = "Puntaje: ${state.score}", 
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        SnakeCanvas(
            state = state,
            modifier = Modifier.size(360.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SnakeDirectionControls(
            onDirectionChange = { newDir ->
                viewModel.changeDirection(newDir)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isGameOver) {
            Text(
                text = "¡JUEGO TERMINADO!", 
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { viewModel.toggleGame() }) { 
                Text(if (state.isPlaying) "Pause" else "Play") 
            }
            Button(onClick = { viewModel.resetGame() }) { 
                Text("Restart") 
            }
            Button(onClick = onBack) { 
                Text("Volver") 
            }
        }
    }
}
