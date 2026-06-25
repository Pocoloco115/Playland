package com.example.playland2.feature.snake.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playland2.feature.snake.data.SnakeRepository
import com.example.playland2.feature.snake.domain.model.Direction
import com.example.playland2.feature.snake.domain.model.Position
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class SnakeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SnakeRepository(application)
    
    private val _state = MutableStateFlow(SnakeGameState(highScore = repository.getHighScore()))
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
                delay(120)
                moveSnake()
            }
        }
    }

    private fun pauseGame() {
        _state.update { it.copy(isPlaying = false) }
        gameJob?.cancel()
    }

    fun resetGame() {
        _state.value = SnakeGameState(highScore = repository.getHighScore())
        lastProcessedDirection = Direction.RIGHT
        nextDirection = Direction.RIGHT
        pauseGame()
    }

    fun changeDirection(newDirection: Direction) {
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
                
                // Game Over - Save High Score
                repository.saveHighScore(currentState.score)
                return@update currentState.copy(
                    isPlaying = false, 
                    isGameOver = true,
                    highScore = repository.getHighScore()
                )
            }

            val newSnake = mutableListOf(newHead) + currentState.snake
            
            if (newHead == currentState.food) {
                val newScore = currentState.score + 10
                val newFood = generateFood(newSnake, currentState.gridSize)
                
                // Update high score in real-time if current score exceeds it
                val currentHigh = if (newScore > currentState.highScore) newScore else currentState.highScore
                
                currentState.copy(
                    snake = newSnake,
                    food = newFood,
                    score = newScore,
                    highScore = currentHigh,
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
