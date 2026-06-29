package com.example.playland2.feature.snake.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playland2.feature.snake.domain.model.Direction
import com.example.playland2.feature.snake.presentation.SnakeViewModel
import kotlin.math.abs

@Composable
fun SnakeScreen(
    onBack: () -> Unit,
    viewModel: SnakeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val availableWidth = maxWidth
        val availableHeight = maxHeight
        val canvasSize = if (availableWidth < availableHeight * 0.6f) {
            availableWidth * 0.9f
        } else {
            availableHeight * 0.5f
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val (x, y) = dragAmount
                            if (abs(x) > abs(y)) {
                                if (x > 0) viewModel.changeDirection(Direction.RIGHT)
                                else viewModel.changeDirection(Direction.LEFT)
                            } else {
                                if (y > 0) viewModel.changeDirection(Direction.DOWN)
                                else viewModel.changeDirection(Direction.UP)
                            }
                        }
                    )
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Snake",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Puntaje: ${state.score}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Record: ${state.highScore}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            SnakeCanvas(
                state = state,
                modifier = Modifier.size(canvasSize)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SnakeDirectionControls(
                onDirectionChange = { newDir ->
                    viewModel.changeDirection(newDir)
                }
            )

            if (state.isGameOver) {
                Text(
                    text = "¡JUEGO TERMINADO!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
}
