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

    // GAME OVER
    var isGameOver by mutableStateOf(false)

    // VELOCIDAD
    var speed by mutableFloatStateOf(10f)

    // PLAYER STATE
    var playerState by mutableStateOf("eat")

    // OBJECTS
    val objects = mutableStateListOf<FallingObject>()

    init {

        repeat(6) {

            spawnFood()
        }

        repeat(2) {

            spawnPoison()
        }
    }

    fun updateGame() {

        if (isGameOver) return

        // AUMENTAR VELOCIDAD
        speed += 0.002f

        objects.forEach { obj ->

            obj.y += speed

            // RESET OBJECT
            if (obj.y > 2200f) {

                obj.y = -200f
                obj.x = Random.nextInt(50, 900).toFloat()
            }

            // COLLISION
            val distance = abs(playerX - obj.x)

            if (
                obj.y > 1700 &&
                distance < 140
            ) {

                if (obj.isPoison) {

                    poisonHits++

                    playerState = "hurt"

                    obj.y = -200f
                    obj.x = Random.nextInt(50, 900).toFloat()

                    // GAME OVER
                    if (poisonHits >= 3) {

                        playerState = "dead"
                        isGameOver = true
                    }

                } else {

                    score++

                    obj.y = -200f
                    obj.x = Random.nextInt(50, 900).toFloat()
                }
            }
        }
    }

    private fun spawnFood() {

        objects.add(

            FallingObject(
                x = Random.nextInt(50, 900).toFloat(),
                y = Random.nextInt(-1500, 0).toFloat(),
                isPoison = false
            )
        )
    }

    private fun spawnPoison() {

        objects.add(

            FallingObject(
                x = Random.nextInt(50, 700).toFloat(),
                y = Random.nextInt(-1500, 0).toFloat(),
                isPoison = true
            )
        )
    }
}