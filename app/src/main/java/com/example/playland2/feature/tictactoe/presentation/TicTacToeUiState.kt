package com.example.playland2.feature.tictactoe.presentation

import com.example.playland2.feature.tictactoe.domain.model.Player

data class TicTacToeGameState(
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,

    val xWins: Int = 0,
    val oWins: Int = 0,
    val draws: Int = 0
)
