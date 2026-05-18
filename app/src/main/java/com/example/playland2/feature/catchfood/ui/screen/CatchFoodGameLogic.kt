package com.example.playland2.feature.catchfood.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.abs
import kotlin.random.Random

data class FallingObject(

    var x: Float,
    var y: Float,
    val isPoison: Boolean
)

class CatchFoodGameLogic {

    // PLAYER
    var playerX by mutableFloatStateOf(300f)

    // SCORE
    var score by mutableIntStateOf(0)

    // VENENO
    var poisonHits by mutableIntStateOf(0)

    // COMIDAS PERDIDAS
    var missedFood by mutableIntStateOf(0)

    // GAME OVER
    var isGameOver by mutableStateOf(false)

    // VELOCIDAD
    var speed by mutableFloatStateOf(8f)

    // PLAYER STATE
    var playerState by mutableStateOf("eat")

    // OBJECTS
    val objects = mutableStateListOf<FallingObject>()

    init {

        repeat(8) { index ->

            spawnFood(index)
        }

        repeat(2) { index ->

            spawnPoison(index)
        }
    }

    fun updateGame() {

        if (isGameOver) return

        // AUMENTA VELOCIDAD
        speed += 0.001f

        objects.forEach { obj ->

            obj.y += speed

            // OBJETO PERDIDO
            if (obj.y > 1900f) {

                if (!obj.isPoison) {

                    missedFood++

                    if (missedFood >= 5) {

                        playerState = "dead"
                        isGameOver = true
                    }
                }

                resetObject(obj)
            }

            // HITBOX
            val distance = abs(playerX - obj.x)

            // SOLO CUENTA SI TOCA AL CONEJO
            if (
                obj.y > 1500 &&
                obj.y < 1750 &&
                distance < 220
            ) {

                // VENENO
                if (obj.isPoison) {

                    poisonHits++

                    playerState = "hurt"

                    if (poisonHits >= 3) {

                        playerState = "dead"
                        isGameOver = true
                    }

                } else {

                    // COMIDA
                    score++
                }

                // DESAPARECE Y REAPARECE
                resetObject(obj)
            }
        }
    }

    private fun resetObject(obj: FallingObject) {

        obj.y = Random.nextInt(-3500, -500).toFloat()

        obj.x = Random.nextInt(80, 850).toFloat()
    }

    private fun spawnFood(index: Int) {

        objects.add(

            FallingObject(

                x = Random.nextInt(80, 850).toFloat(),

                // UNO TRAS OTRO
                y = (-500f * index),

                isPoison = false
            )
        )
    }

    private fun spawnPoison(index: Int) {

        objects.add(

            FallingObject(

                x = Random.nextInt(80, 850).toFloat(),

                y = (-3500f - (700f * index)),

                isPoison = true
            )
        )
    }
}