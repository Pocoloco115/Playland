package com.example.playland2.feature.catchfood.ui.screen

data class CatchFoodUiState(
    val score: Int = 0,
    val poisonHits: Int = 0,
    val missedFood: Int = 0,
    val playerState: String = "eat",
    val isPaused: Boolean = false,
    val isGameOver: Boolean = false
)
