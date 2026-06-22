package com.example.playland2.feature.catchfood.ui.screen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playland2.feature.catchfood.data.CatchFoodScoreRepository
import kotlinx.coroutines.launch

class CatchFoodViewModel(application: Application) : AndroidViewModel(application) {
    val gameLogic = CatchFoodGameLogic()

    private val scoreRepository = CatchFoodScoreRepository(application)
    private var scoreSavedForCurrentGame = false

    var uiState by mutableStateOf(gameLogic.toUiState())
        private set

    fun updateGame(deltaSeconds: Float) {
        gameLogic.updateGame(deltaSeconds)
        syncUiState()

        if (gameLogic.isGameOver && !scoreSavedForCurrentGame) {
            scoreSavedForCurrentGame = true
            val finalScore = gameLogic.score
            viewModelScope.launch {
                scoreRepository.saveScore(finalScore)
            }
        }
    }

    fun movePlayer(deltaX: Float) {
        gameLogic.movePlayer(deltaX)
    }

    fun setViewportHeight(heightDp: Float) {
        gameLogic.setViewportHeight(heightDp)
    }

    fun pause() {
        if (!uiState.isGameOver) {
            uiState = uiState.copy(isPaused = true)
        }
    }

    fun resume() {
        uiState = uiState.copy(isPaused = false)
    }

    fun restart() {
        gameLogic.restart()
        scoreSavedForCurrentGame = false
        uiState = gameLogic.toUiState()
    }

    private fun syncUiState() {
        if (
            uiState.score != gameLogic.score ||
            uiState.poisonHits != gameLogic.poisonHits ||
            uiState.missedFood != gameLogic.missedFood ||
            uiState.playerState != gameLogic.playerState ||
            uiState.isGameOver != gameLogic.isGameOver
        ) {
            uiState = gameLogic.toUiState(isPaused = uiState.isPaused)
        }
    }

    private fun CatchFoodGameLogic.toUiState(isPaused: Boolean = false) = CatchFoodUiState(
        score = score,
        poisonHits = poisonHits,
        missedFood = missedFood,
        playerState = playerState,
        isPaused = isPaused,
        isGameOver = isGameOver
    )
}
