package com.example.playland2.feature.flappybird

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun FlappyBirdCanvas(gameState: FlappyBirdGameState) {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        gameState.screenHeight = size.height

        val screenHeight = size.height

        // Fondo
        drawRect(
            color = Color.Cyan,
            size = size
        )

        // Tubos
        gameState.pipes.forEach { pipe ->

            drawRect(
                color = Color.Green,
                topLeft = Offset(pipe.x, 0f),
                size = Size(
                    pipe.width,
                    pipe.gapY
                )
            )

            drawRect(
                color = Color.Green,
                topLeft = Offset(
                    pipe.x,
                    pipe.gapY + pipe.gapHeight
                ),
                size = Size(
                    pipe.width,
                    screenHeight - (
                            pipe.gapY + pipe.gapHeight
                            )
                )
            )
        }

        // Pájaro
        drawCircle(
            color = Color.Yellow,
            radius = 70f,
            center = Offset(
                120f,
                gameState.birdY
            )
        )
    }
}