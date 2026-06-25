package com.example.playland2.feature.snake.presentation

import com.example.playland2.feature.snake.domain.model.Direction
import com.example.playland2.feature.snake.domain.model.Position

data class SnakeGameState(
    val snake: List<Position> = listOf(Position(10, 10), Position(9, 10), Position(8, 10)),
    val direction: Direction = Direction.RIGHT,
    val food: Position = Position(15, 15),
    val score: Int = 0,
    val highScore: Int = 0,
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false,
    val gridSize: Int = 20
)
