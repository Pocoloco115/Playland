package com.example.playland2.feature.tictactoe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playland2.feature.tictactoe.domain.model.Player

@Composable
fun TicTacToeBoard(
    board: List<Player?>,
    onCellClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.size(300.dp),
        userScrollEnabled = false
    ) {
        items(9) { index ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
                    .background(Color.LightGray)
                    .clickable { onCellClick(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = board[index]?.name ?: "",
                    fontSize = 38.sp
                )
            }
        }
    }
}