package com.example.playland2.feature.catchfood.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

data class FallingObject(

    var x: Float,
    var y: Float,
    val isPoison: Boolean
)

class CatchFoodGameLogic {

    var playerX by mutableFloatStateOf(300f)
    var score by mutableIntStateOf(0)
    var poisonHits by mutableIntStateOf(0)
    var missedFood by mutableIntStateOf(0)
    var isGameOver by mutableStateOf(false)
    var speed = INITIAL_SPEED
        private set
    var playerState by mutableStateOf("eat")
    val objects = ArrayList<FallingObject>(OBJECT_COUNT)
    var frameVersion by mutableIntStateOf(0)
        private set
    private var viewportHeightDp = 0f

    init {
        createObjects()
    }

    fun updateGame(deltaSeconds: Float) {

        if (isGameOver) return

        // aqui incremento la velocidad
        val frameScale = (deltaSeconds * TARGET_FPS).coerceIn(0f, MAX_FRAME_SCALE)
        speed += SPEED_INCREASE_PER_FRAME * frameScale

        objects.forEach { obj ->

            obj.y += speed * frameScale

            val objectLeft = obj.x / WORLD_SCALE + OBJECT_HITBOX_INSET_X_DP
            val objectTop = obj.y / WORLD_SCALE + OBJECT_TOP_OFFSET_DP
            val playerLeft = playerX / WORLD_SCALE + PLAYER_HITBOX_INSET_X_DP
            val playerTop = viewportHeightDp - PLAYER_SIZE_DP - PLAYER_BOTTOM_OFFSET_DP +
                PLAYER_HITBOX_INSET_TOP_DP

            // collision
            val touchesPlayer = viewportHeightDp > 0f &&
                objectLeft < playerLeft + PLAYER_HITBOX_WIDTH_DP &&
                objectLeft + OBJECT_HITBOX_WIDTH_DP > playerLeft &&
                objectTop + OBJECT_HITBOX_INSET_TOP_DP < playerTop + PLAYER_HITBOX_HEIGHT_DP &&
                objectTop + OBJECT_HITBOX_INSET_TOP_DP + OBJECT_HITBOX_HEIGHT_DP > playerTop

            if (touchesPlayer) {
                if (obj.isPoison) {
                    poisonHits++
                    playerState = "hurt"

                    if (poisonHits >= 3) {
                        playerState = "dead"
                        isGameOver = true
                    }
                } else {
                    score++
                }

                resetObject(obj)
                return@forEach
            }

            // objeto perdido
            if (viewportHeightDp > 0f && objectTop > viewportHeightDp) {

                if (!obj.isPoison) {

                    missedFood++

                    if (missedFood >= 5) {

                        playerState = "dead"
                        isGameOver = true
                    }
                }

                resetObject(obj)
            }
        }

        frameVersion++
    }

    fun movePlayer(deltaX: Float) {
        playerX = (playerX + deltaX).coerceIn(0f, MAX_PLAYER_X)
    }

    fun setViewportHeight(heightDp: Float) {
        viewportHeightDp = heightDp
    }

    fun restart() {
        objects.clear()
        playerX = 300f
        score = 0
        poisonHits = 0
        missedFood = 0
        speed = INITIAL_SPEED
        playerState = "eat"
        isGameOver = false

        createObjects()
        frameVersion++
    }

    private fun resetObject(obj: FallingObject) {
        val objectBehindQueue = objects
            .asSequence()
            .filter { it !== obj }
            .minOfOrNull { it.y }
            ?: INITIAL_SPAWN_Y

        obj.y = minOf(objectBehindQueue - SPAWN_SPACING_WORLD, INITIAL_SPAWN_Y)
        obj.x = Random.nextInt(80, 850).toFloat()
    }

    private fun createObjects() {
        val objectTypes = (List(8) { false } + List(2) { true }).shuffled()
        objectTypes.forEachIndexed { index, isPoison ->
            objects.add(
                FallingObject(
                    x = Random.nextInt(80, 850).toFloat(),
                    y = INITIAL_SPAWN_Y - (SPAWN_SPACING_WORLD * index),
                    isPoison = isPoison
                )
            )
        }
    }

    private companion object {
        const val TARGET_FPS = 60f
        const val MAX_FRAME_SCALE = 3f
        const val SPEED_INCREASE_PER_FRAME = 0.001f
        const val INITIAL_SPEED = 8f
        const val MAX_PLAYER_X = 850f
        const val OBJECT_COUNT = 10
        const val WORLD_SCALE = 3f
        const val OBJECT_TOP_OFFSET_DP = 220f
        const val OBJECT_SIZE_DP = 90f
        const val PLAYER_SIZE_DP = 170f
        const val PLAYER_BOTTOM_OFFSET_DP = 40f
        const val INITIAL_SPAWN_Y = -500f
        const val SPAWN_SPACING_WORLD = 650f
        const val PLAYER_HITBOX_INSET_X_DP = 45f
        const val PLAYER_HITBOX_INSET_TOP_DP = 50f
        const val PLAYER_HITBOX_WIDTH_DP = 80f
        const val PLAYER_HITBOX_HEIGHT_DP = 75f
        const val OBJECT_HITBOX_INSET_X_DP = 18f
        const val OBJECT_HITBOX_INSET_TOP_DP = 12f
        const val OBJECT_HITBOX_WIDTH_DP = 54f
        const val OBJECT_HITBOX_HEIGHT_DP = 66f
    }
}
