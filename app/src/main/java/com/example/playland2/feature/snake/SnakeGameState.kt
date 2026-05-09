package com.example.playland2.feature.snake

data class SnakeGameState(
    val snake: List<Position> = listOf(Position(10, 10), Position(9, 10), Position(8, 10)),
    val direction: Direction = Direction.RIGHT,
    val food: Position = Position(15, 15),
    val score: Int = 0,
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false,
    val gridSize: Int = 20
)