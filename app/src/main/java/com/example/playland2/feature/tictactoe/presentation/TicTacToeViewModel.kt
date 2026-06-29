package com.example.playland2.feature.tictactoe.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playland2.feature.tictactoe.data.TicTacToeDatabase
import com.example.playland2.feature.tictactoe.data.TicTacToeScoreEntity
import com.example.playland2.feature.tictactoe.domain.model.Player
import kotlinx.coroutines.launch

class TicTacToeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = TicTacToeDatabase.getInstance(application).ticTacToeScoreDao()

    var state = mutableStateOf(TicTacToeGameState())
        private set

    init {
        viewModelScope.launch {
            dao.getScore().collect { entity ->
                entity?.let {
                    state.value = state.value.copy(
                        xWins = it.xWins,
                        oWins = it.oWins,
                        draws = it.draws
                    )
                }
            }
        }
    }

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

        val newState = when {
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
        
        state.value = newState

        if (winner != null || draw) {
            viewModelScope.launch {
                dao.insertScore(
                    TicTacToeScoreEntity(
                        xWins = newState.xWins,
                        oWins = newState.oWins,
                        draws = newState.draws
                    )
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
        viewModelScope.launch {
            dao.clearScores()
            state.value = TicTacToeGameState()
        }
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
