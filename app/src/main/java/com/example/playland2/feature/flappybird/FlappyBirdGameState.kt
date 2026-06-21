package com.example.playland2.feature.flappybird

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FlappyBirdGameState {

    var birdY by mutableStateOf(600f)

    var birdVelocity by mutableStateOf(0f)

    var pipes = mutableListOf<Pipe>()

    var score by mutableStateOf(0)

    var highScore by mutableStateOf(0)

    var isGameOver by mutableStateOf(false)

    var screenHeight by mutableStateOf(0f)

    var hasStarted by mutableStateOf(false)
}
