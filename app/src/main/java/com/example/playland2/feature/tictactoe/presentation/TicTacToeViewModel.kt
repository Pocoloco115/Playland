package com.example.playland2.feature.tictactoe.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.playland2.feature.tictactoe.domain.model.Player

class TicTacToeViewModel : ViewModel() {

    var state = mutableStateOf(TicTacToeGameState())
        private set

    fun onCellClick(index: Int) {
        val currentState = state.value

        if (
            currentState.board[index] != null ||
            currentState.winner != null ||
            currentState.isDraw
        ) return

        val newBoard = currentState.board.toMutableList()
        newBoard[index] = currentState.currentPlayer

        val winner = checkWinner(newBoard)
        val draw = winner == null && newBoard.none { it == null }

        state.value = when {
            winner != null -> {
                currentState.copy(
                    board = newBoard,
                    winner = winner,
                    xWins = if (winner == Player.X) currentState.xWins + 1 else currentState.xWins,
                    oWins = if (winner == Player.O) currentState.oWins + 1 else currentState.oWins
                )
            }

            draw -> {
                currentState.copy(
                    board = newBoard,
                    isDraw = true,
                    draws = currentState.draws + 1
                )
            }

            else -> {
                currentState.copy(
                    board = newBoard,
                    currentPlayer = currentState.currentPlayer.next()
                )
            }
        }
    }

    fun resetGame() {
        state.value = state.value.copy(
            board = List(9) { null },
            currentPlayer = Player.X,
            winner = null,
            isDraw = false
        )
    }

    fun resetScore() {
        state.value = TicTacToeGameState()
    }

    private fun checkWinner(board: List<Player?>): Player? {
        val combinations = listOf(
            listOf(0,1,2),
            listOf(3,4,5),
            listOf(6,7,8),
            listOf(0,3,6),
            listOf(1,4,7),
            listOf(2,5,8),
            listOf(0,4,8),
            listOf(2,4,6)
        )

        for (combo in combinations) {
            val (a, b, c) = combo

            if (
                board[a] != null &&
                board[a] == board[b] &&
                board[a] == board[c]
            ) {
                return board[a]
            }
        }

        return null
    }
}