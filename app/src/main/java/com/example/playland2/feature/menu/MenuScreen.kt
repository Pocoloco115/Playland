package com.example.playland2.feature.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onNavigateToSnake: () -> Unit,
    onNavigateToTicTacToe: () -> Unit,
    onNavigateToCatchFood: () -> Unit,
    onNavigateToFlappyBird: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "PLAYLAND", style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateToSnake){ Text("Snake") }
        Button(onClick = onNavigateToFlappyBird) { Text("Flappy Bird") }
        Button(onClick = onNavigateToCatchFood) { Text("Catch Food") }
        Button(onClick = onNavigateToTicTacToe) { Text("Tic Tac Toe") }
    }
}
