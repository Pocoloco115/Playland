package com.example.playland2.feature.snake

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size as CoilSize

@Composable
fun SnakeCanvas(
    state: SnakeGameState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val gridPainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data("file:///android_asset/games/snake/grid_tile.png")
            .size(CoilSize.ORIGINAL)
            .build()
    )

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

        for (y in 0 until state.gridSize) {
            for (x in 0 until state.gridSize) {
                translate(left = x * cellSize, top = y * cellSize) {
                    with(gridPainter) {
                        draw(size = Size(cellSize, cellSize))
                    }
                }
            }
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
