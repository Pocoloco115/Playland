package com.example.playland2.feature.tictactoe.presentation

import androidx.lifecycle.ViewModel
import com.example.playland2.feature.tictactoe.domain.model.Player
import com.example.playland2.feature.tictactoe.domain.model.TicTacToeCell
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicTacToeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TicTacToeUiState())
    val uiState: StateFlow<TicTacToeUiState> = _uiState.asStateFlow()

    fun onCellClicked(index: Int) {
        val currentState = _uiState.value.board
        if (currentState.cells[index] is TicTacToeCell.Empty && currentState.winner == null && !currentState.isDraw) {
            val newCells = currentState.cells.toMutableList()
            newCells[index] = TicTacToeCell.Filled(currentState.currentPlayer)
            
            val winner = checkWinner(newCells)
            val isDraw = winner == null && newCells.none { it is TicTacToeCell.Empty }
            val nextPlayer = if (currentState.currentPlayer == Player.X) Player.O else Player.X

            _uiState.update { 
                it.copy(
                    board = currentState.copy(
                        cells = newCells,
                        currentPlayer = nextPlayer,
                        winner = winner,
                        isDraw = isDraw
                    )
                )
            }
        }
    }

    fun resetGame() {
        _uiState.update { TicTacToeUiState() }
    }

    private fun checkWinner(cells: List<TicTacToeCell>): Player? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
            listOf(0, 4, 8), listOf(2, 4, 6)             // Diagonals
        )

        for (pattern in winPatterns) {
            val (a, b, c) = pattern
            val cellA = cells[a]
            val cellB = cells[b]
            val cellC = cells[c]

            if (cellA is TicTacToeCell.Filled && cellB is TicTacToeCell.Filled && cellC is TicTacToeCell.Filled) {
                if (cellA.player == cellB.player && cellB.player == cellC.player) {
                    return cellA.player
                }
            }
        }
        return null
    }
}
