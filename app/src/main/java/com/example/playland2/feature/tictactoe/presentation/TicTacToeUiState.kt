package com.example.playland2.feature.tictactoe.presentation

import com.example.playland2.feature.tictactoe.domain.model.TicTacToeBoard

data class TicTacToeUiState(
    val board: TicTacToeBoard = TicTacToeBoard()
)
