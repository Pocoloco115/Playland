package com.example.playland2.feature.flappybird

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlin.random.Random

class FlappyBirdViewModel(application: Application) : AndroidViewModel(application) {

    val gameState = FlappyBirdGameState()

    // =========================
    // SHARED PREFERENCES
    // =========================

    private val prefs = application.getSharedPreferences(
        "flappy_bird_prefs",
        Context.MODE_PRIVATE
    )

    // =========================
    // FÍSICA
    // =========================

    private val gravity = 1.80f

    private val jumpForce = -28f

    private val maxFallSpeed = 36f

    // =========================
    // TUBOS
    // =========================

    private val pipeSpeed = 14f

    private val pipeGap = 700f

    private val pipeWidth = 200f

    private val pipeSpacing = 850f

    // =========================
    // PÁJARO
    // =========================

    private val birdRadius = 80f

    init {

        gameState.highScore = prefs.getInt(
            "high_score",
            0
        )
    }

    fun updateGame(screenHeight: Float) {

        if (gameState.isGameOver) return

        // Esperar primer tap
        if (!gameState.hasStarted) {
            return
        }

        // Gravedad
        gameState.birdVelocity += gravity

        // Límite caída
        if (gameState.birdVelocity > maxFallSpeed) {
            gameState.birdVelocity = maxFallSpeed
        }

        // Movimiento vertical
        gameState.birdY += gameState.birdVelocity

        // Mover tubos
        gameState.pipes.forEach {
            it.x -= pipeSpeed
        }

        // Generar tubos
        if (
            gameState.pipes.isEmpty() ||
            gameState.pipes.last().x <
            (gameState.screenHeight - pipeSpacing)
        ) {

            val gapY = Random.nextInt(
                250,
                (screenHeight - 950).toInt()
            ).toFloat()

            gameState.pipes.add(
                Pipe(
                    x = screenHeight + 600f,
                    gapY = gapY,
                    gapHeight = pipeGap,
                    width = pipeWidth
                )
            )
        }

        // Eliminar tubos
        gameState.pipes.removeAll {
            it.x + it.width < 0
        }

        // Score
        gameState.pipes.forEach { pipe ->

            if (
                !pipe.passed &&
                pipe.x + pipe.width < 120f
            ) {

                pipe.passed = true

                gameState.score++

                // Guardar récord
                if (gameState.score > gameState.highScore) {

                    gameState.highScore = gameState.score

                    prefs.edit()
                        .putInt(
                            "high_score",
                            gameState.highScore
                        )
                        .apply()
                }
            }
        }

        // Colisiones
        checkCollision(screenHeight)
    }

    fun jump() {

        if (gameState.isGameOver) return

        // Primer tap inicia el juego
        if (!gameState.hasStarted) {
            gameState.hasStarted = true
        }

        gameState.birdVelocity = jumpForce
    }

    fun resetGame(screenHeight: Float) {

        gameState.birdY = screenHeight / 2f

        gameState.birdVelocity = 0f

        gameState.pipes.clear()

        gameState.score = 0

        gameState.isGameOver = false

        gameState.hasStarted = false
    }

    private fun checkCollision(screenHeight: Float) {

        val birdX = 120f

        val hitboxRadius = birdRadius - 15f

        val birdTop = gameState.birdY - hitboxRadius

        val birdBottom = gameState.birdY + hitboxRadius

        val birdLeft = birdX - hitboxRadius

        val birdRight = birdX + hitboxRadius

        gameState.pipes.forEach { pipe ->

            val pipeLeft = pipe.x

            val pipeRight = pipe.x + pipe.width

            val collisionX =
                birdRight > pipeLeft &&
                        birdLeft < pipeRight

            val collisionTop =
                birdTop < pipe.gapY

            val collisionBottom =
                birdBottom > pipe.gapY + pipe.gapHeight

            if (
                collisionX &&
                (collisionTop || collisionBottom)
            ) {

                gameState.isGameOver = true
            }
        }

        // Techo
        if (birdTop <= 0f) {
            gameState.isGameOver = true
        }

        // Suelo
        if (birdBottom >= screenHeight) {
            gameState.isGameOver = true
        }
    }
}