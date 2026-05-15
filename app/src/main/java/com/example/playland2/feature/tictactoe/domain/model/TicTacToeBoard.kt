package com.example.playland2.feature.tictactoe.domain.model

enum class Player {
    X, O
}

sealed class TicTacToeCell {
    object Empty : TicTacToeCell()
    data class Filled(val player: Player) : TicTacToeCell()
}

data class TicTacToeBoard(
    val cells: List<TicTacToeCell> = List(9) { TicTacToeCell.Empty },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false
)
