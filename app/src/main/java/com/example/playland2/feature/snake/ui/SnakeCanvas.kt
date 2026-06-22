package com.example.playland2.feature.snake.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size as CoilSize
import com.example.playland2.feature.snake.presentation.SnakeGameState

@Composable
fun SnakeCanvas(
    state: SnakeGameState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val headPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/games/snake/head_${state.direction.name.lowercase()}.png")
            .size(CoilSize.ORIGINAL)
            .build()
    )

    val bodyPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/games/snake/body.png")
            .size(CoilSize.ORIGINAL)
            .build()
    )

    val foodPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/games/snake/food.png")
            .size(CoilSize.ORIGINAL)
            .build()
    )

    Canvas(modifier = modifier) {
        val cellSize = size.width / state.gridSize

        drawRect(color = Color(0xFF1A1A1B))
        
        for (i in 0..state.gridSize) {
            drawLine(
                color = Color.White.copy(alpha = 0.05f),
                start = Offset(i * cellSize, 0f),
                end = Offset(i * cellSize, size.height),
                strokeWidth = 1f
            )
            drawLine(
                color = Color.White.copy(alpha = 0.05f),
                start = Offset(0f, i * cellSize),
                end = Offset(size.width, i * cellSize),
                strokeWidth = 1f
            )
        }

        translate(
            left = state.food.x * cellSize,
            top = state.food.y * cellSize
        ) {
            with(foodPainter) {
                draw(size = Size(cellSize, cellSize))
            }
        }

        state.snake.forEachIndexed { index, pos ->
            val painter = if (index == 0) headPainter else bodyPainter
            translate(
                left = pos.x * cellSize,
                top = pos.y * cellSize
            ) {
                with(painter) {
                    draw(size = Size(cellSize, cellSize))
                }
            }
        }
    }
}
