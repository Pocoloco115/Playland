package com.example.playland2.feature.snake.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playland2.feature.snake.domain.model.Direction
import com.example.playland2.feature.snake.domain.model.Position
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SnakeViewModel : ViewModel() {
    private val _state = MutableStateFlow(SnakeGameState())
    val state = _state.asStateFlow()

    private var gameJob: Job? = null
    private var lastProcessedDirection: Direction = Direction.RIGHT
    private var nextDirection: Direction = Direction.RIGHT

    fun toggleGame() {
        if (_state.value.isPlaying) {
            pauseGame()
        } else {
            startGame()
        }
    }

    private fun startGame() {
        if (_state.value.isGameOver) {
            resetGame()
        }
        _state.update { it.copy(isPlaying = true) }
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (_state.value.isPlaying) {
                delay(120) // Un poco más rápido para mejor respuesta
                moveSnake()
            }
        }
    }

    private fun pauseGame() {
        _state.update { it.copy(isPlaying = false) }
        gameJob?.cancel()
    }

    fun resetGame() {
        _state.value = SnakeGameState()
        lastProcessedDirection = Direction.RIGHT
        nextDirection = Direction.RIGHT
        pauseGame()
    }

    fun changeDirection(newDirection: Direction) {
        // Evitamos que cambie a la dirección opuesta de la que se está procesando actualmente
        if (!lastProcessedDirection.isOpposite(newDirection)) {
            nextDirection = newDirection
        }
    }

    private fun moveSnake() {
        _state.update { currentState ->
            lastProcessedDirection = nextDirection
            val head = currentState.snake.first()
            val newHead = when (lastProcessedDirection) {
                Direction.UP -> Position(head.x, head.y - 1)
                Direction.DOWN -> Position(head.x, head.y + 1)
                Direction.LEFT -> Position(head.x - 1, head.y)
                Direction.RIGHT -> Position(head.x + 1, head.y)
            }

            if (newHead.x !in 0 until currentState.gridSize || 
                newHead.y !in 0 until currentState.gridSize ||
                currentState.snake.contains(newHead)) {
                return@update currentState.copy(isPlaying = false, isGameOver = true)
            }

            val newSnake = mutableListOf(newHead) + currentState.snake
            
            if (newHead == currentState.food) {
                val newFood = generateFood(newSnake, currentState.gridSize)
                currentState.copy(
                    snake = newSnake,
                    food = newFood,
                    score = currentState.score + 10,
                    direction = lastProcessedDirection
                )
            } else {
                currentState.copy(
                    snake = newSnake.dropLast(1),
                    direction = lastProcessedDirection
                )
            }
        }
    }

    private fun generateFood(snake: List<Position>, gridSize: Int): Position {
        var newFood: Position
        do {
            newFood = Position(Random.nextInt(gridSize), Random.nextInt(gridSize))
        } while (snake.contains(newFood))
        return newFood
    }
}
