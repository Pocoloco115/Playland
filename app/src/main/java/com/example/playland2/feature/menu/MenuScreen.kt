package com.example.playland2.feature.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MenuScreen(
    onNavigateToSnake: () -> Unit,
    onNavigateToTicTacToe: () -> Unit,
    onNavigateToCatchFood: () -> Unit,
    onNavigateToFlappyBird: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PLAYLAND",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GameIconButton(
                iconUrl = "file:///android_asset/icons/snake.png",
                label = "Snake",
                onClick = onNavigateToSnake
            )
            GameIconButton(
                iconUrl = "file:///android_asset/icons/flappy-bird.png",
                label = "Flappy",
                onClick = onNavigateToFlappyBird
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GameIconButton(
                iconUrl = "file:///android_asset/icons/comer.png",
                label = "Catch Food",
                onClick = onNavigateToCatchFood
            )
            GameIconButton(
                iconUrl = "file:///android_asset/icons/ttt.png",
                label = "TicTacToe",
                onClick = onNavigateToTicTacToe
            )
        }
    }
}

@Composable
fun GameIconButton(
    iconUrl: String,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(iconUrl),
                contentDescription = label,
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
