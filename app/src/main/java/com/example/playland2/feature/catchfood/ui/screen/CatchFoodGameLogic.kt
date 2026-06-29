package com.example.playland2.feature.catchfood.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

class CatchFoodGameLogic {

    var playerX by mutableFloatStateOf(300f)
    var score by mutableIntStateOf(0)
    var poisonHits by mutableIntStateOf(0)
    var missedFood by mutableIntStateOf(0)
    var isGameOver by mutableStateOf(false)
    var speed = velocidadInicial
        private set
    var playerState by mutableStateOf("eat")
    val objects = ArrayList<FallingObject>(cantidadObjetos)
    var frameVersion by mutableIntStateOf(0)
        private set
    private var viewportHeightDp = 0f

    init {
        createObjects()
    }

    fun updateGame(deltaSeconds: Float) {

        if (isGameOver) return

        // aqui incremento la velocidad
        val escalaDelFrame = (deltaSeconds * fpsObjetivo).coerceIn(0f, escalaMaximaFrame)
        speed += aumentoVelocidadPorFrame * escalaDelFrame

        objects.forEach { obj ->

            obj.y += speed * escalaDelFrame

            val izquierdaObjeto = obj.x / escalaMundo + margenXHitboxObjetoDp
            val arribaObjeto = obj.y / escalaMundo + distanciaSuperiorObjetoDp
            val izquierdaJugador = playerX / escalaMundo + margenXHitboxJugadorDp
            val arribaJugador = viewportHeightDp - tamanoJugadorDp - distanciaInferiorJugadorDp +
                margenSuperiorHitboxJugadorDp

            // collision
            val tocaJugador = viewportHeightDp > 0f &&
                izquierdaObjeto < izquierdaJugador + anchoHitboxJugadorDp &&
                izquierdaObjeto + anchoHitboxObjetoDp > izquierdaJugador &&
                arribaObjeto + margenSuperiorHitboxObjetoDp < arribaJugador + altoHitboxJugadorDp &&
                arribaObjeto + margenSuperiorHitboxObjetoDp + altoHitboxObjetoDp > arribaJugador

            if (tocaJugador) {
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
            if (viewportHeightDp > 0f && arribaObjeto > viewportHeightDp) {

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
        playerX = (playerX + deltaX).coerceIn(0f, posicionMaximaJugadorX)
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
        speed = velocidadInicial
        playerState = "eat"
        isGameOver = false

        createObjects()
        frameVersion++
    }

    private fun resetObject(obj: FallingObject) {
        val objetoMasArribaEnCola = objects
            .asSequence()
            .filter { it !== obj }
            .minOfOrNull { it.y }
            ?: posicionInicialAparicionY

        obj.y = minOf(objetoMasArribaEnCola - separacionAparicionMundo, posicionInicialAparicionY)
        obj.x = Random.nextInt(80, 850).toFloat()
    }

    private fun createObjects() {
        val tiposDeObjeto = (List(8) { false } + List(2) { true }).shuffled()
        tiposDeObjeto.forEachIndexed { index, isPoison ->
            objects.add(
                FallingObject(
                    x = Random.nextInt(80, 850).toFloat(),
                    y = posicionInicialAparicionY - (separacionAparicionMundo * index),
                    isPoison = isPoison
                )
            )
        }
    }

    private companion object {
        const val fpsObjetivo = 60f
        const val escalaMaximaFrame = 3f
        const val aumentoVelocidadPorFrame = 0.001f
        const val velocidadInicial = 8f
        const val posicionMaximaJugadorX = 850f
        const val cantidadObjetos = 10
        const val escalaMundo = 3f
        const val distanciaSuperiorObjetoDp = 220f
        const val tamanoJugadorDp = 170f
        const val distanciaInferiorJugadorDp = 40f
        const val posicionInicialAparicionY = -500f
        const val separacionAparicionMundo = 650f
        const val margenXHitboxJugadorDp = 45f
        const val margenSuperiorHitboxJugadorDp = 50f
        const val anchoHitboxJugadorDp = 80f
        const val altoHitboxJugadorDp = 75f
        const val margenXHitboxObjetoDp = 18f
        const val margenSuperiorHitboxObjetoDp = 12f
        const val anchoHitboxObjetoDp = 54f
        const val altoHitboxObjetoDp = 66f
    }
}
