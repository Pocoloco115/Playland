package com.example.playland2.feature.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(onBack: () -> Unit) {

    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }

    var scoreX by remember { mutableStateOf(0) }
    var scoreO by remember { mutableStateOf(0) }

    fun checkWinner(board: List<String>): String {
        val winningPositions = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        for (position in winningPositions) {
            val (a, b, c) = position

            if (
                board[a].isNotEmpty() &&
                board[a] == board[b] &&
                board[b] == board[c]
            ) {
                return board[a]
            }
        }

        return if (board.none { it.isEmpty() }) "Empate" else ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Juego X 0", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("X: $scoreX", fontSize = 22.sp)
            Text("O: $scoreO", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .background(
                                Color.LightGray,
                                RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                if (board[index].isEmpty() && winner.isEmpty()) {
                                    val newBoard = board.toMutableList()
                                    newBoard[index] = currentPlayer
                                    board = newBoard

                                    winner = checkWinner(board)

                                    if (winner == "X") scoreX++
                                    if (winner == "O") scoreO++

                                    if (winner.isEmpty()) {
                                        currentPlayer =
                                            if (currentPlayer == "X") "O" else "X"
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board[index],
                            fontSize = 40.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = when {
                winner == "Empate" -> "Empate"
                winner.isNotEmpty() -> "Gano: $winner"
                else -> "Turno: $currentPlayer"
            },
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(
                onClick = {
                    board = List(9) { "" }
                    currentPlayer = "X"
                    winner = ""
                }
            ) {
                Text("Nueva partida")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    board = List(9) { "" }
                    currentPlayer = "X"
                    winner = ""
                    scoreX = 0
                    scoreO = 0
                }
            ) {
                Text("Reset score")
            }
            Button(onClick = onBack) {
                Text("Volver")
            }
        }
    }

}
