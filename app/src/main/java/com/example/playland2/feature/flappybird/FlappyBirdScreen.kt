package com.example.playland2.feature.flappybird

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

@Composable
fun FlappyBirdScreen(
    onBack: () -> Unit,
    viewModel: FlappyBirdViewModel = viewModel()
) {

    val gameState = viewModel.gameState

    // Centrar pájaro correctamente
    LaunchedEffect(gameState.screenHeight) {

        if (
            gameState.screenHeight > 0f &&
            gameState.birdY == 600f
        ) {

            viewModel.resetGame(
                gameState.screenHeight
            )
        }
    }

    // Game loop
    LaunchedEffect(Unit) {

        while (true) {

            if (
                !gameState.isGameOver &&
                gameState.screenHeight > 0f
            ) {

                viewModel.updateGame(
                    gameState.screenHeight
                )
            }

            delay(16L)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(gameState.isGameOver) {

                // SOLO detectar taps si NO hay game over
                while (!gameState.isGameOver) {

                    awaitPointerEventScope {

                        awaitPointerEvent()

                        viewModel.jump()
                    }
                }
            }
    ) {

        FlappyBirdCanvas(gameState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Score: ${gameState.score}",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Récord: ${gameState.highScore}",
                style = MaterialTheme.typography.titleLarge
            )

            if (!gameState.hasStarted) {

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Tap para empezar",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        if (gameState.isGameOver) {

            Card(
                modifier = Modifier.align(Alignment.Center)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "GAME OVER",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = "Puntuación: ${gameState.score}"
                    )

                    Text(
                        text = "Récord: ${gameState.highScore}"
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Row {

                        Button(
                            onClick = {
                                viewModel.resetGame(
                                    gameState.screenHeight
                                )
                            }
                        ) {
                            Text("Reintentar")
                        }

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Button(
                            onClick = onBack
                        ) {
                            Text("Salir")
                        }
                    }
                }
            }
        }
    }
}