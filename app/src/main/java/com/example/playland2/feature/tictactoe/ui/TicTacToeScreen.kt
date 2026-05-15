package com.example.playland2.feature.tictactoe.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playland2.feature.tictactoe.presentation.TicTacToeViewModel

@Composable
fun TicTacToeScreen(
    onBack: () -> Unit,
    viewModel: TicTacToeViewModel = viewModel()
) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Turno: ${state.currentPlayer}")

        Spacer(modifier = Modifier.height(12.dp))

        Text("X Wins: ${state.xWins}")
        Text("O Wins: ${state.oWins}")
        Text("Empates: ${state.draws}")

        Spacer(modifier = Modifier.height(20.dp))

        TicTacToeBoard(
            board = state.board,
            onCellClick = viewModel::onCellClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            state.winner != null -> {
                Text("Ganó ${state.winner}")
            }
            state.isDraw -> {
                Text("Empate")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { viewModel.resetGame() }) {
                Text("Reiniciar")
            }

            Button(onClick = { viewModel.resetScore() }) {
                Text("Reset Score")
            }

            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }
}
