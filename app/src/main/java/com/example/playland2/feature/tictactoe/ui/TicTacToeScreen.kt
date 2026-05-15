package com.example.playland2.feature.tictactoe.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playland2.feature.tictactoe.domain.model.Player
import com.example.playland2.feature.tictactoe.domain.model.TicTacToeCell
import com.example.playland2.feature.tictactoe.presentation.TicTacToeViewModel

@Composable
fun TicTacToeScreen(
    onBack: () -> Unit,
    viewModel: TicTacToeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val board = uiState.board

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic Tac Toe",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = when {
                board.winner != null -> "Ganador: ${board.winner}"
                board.isDraw -> "Empate"
                else -> "Turno de: ${board.currentPlayer}"
            },
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(2.dp, MaterialTheme.colorScheme.primary),
            userScrollEnabled = false
        ) {
            items(9) { index ->
                val cell = board.cells[index]
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .border(1.dp, MaterialTheme.colorScheme.outline)
                        .clickable { viewModel.onCellClicked(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (cell) {
                            is TicTacToeCell.Empty -> ""
                            is TicTacToeCell.Filled -> cell.player.name
                        },
                        fontSize = 40.sp,
                        color = when (cell) {
                            is TicTacToeCell.Filled -> if (cell.player == Player.X) Color.Red else Color.Blue
                            else -> Color.Unspecified
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { viewModel.resetGame() }) {
                Text("Reiniciar")
            }
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}
