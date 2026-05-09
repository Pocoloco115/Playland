package com.example.playland2.feature.snake

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun SnakeDirectionControls(
    onDirectionChange: (Direction) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { onDirectionChange(Direction.UP) },
            modifier = Modifier.size(64.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("file:///android_asset/games/snake/arrow_up.png"),
                contentDescription = "Arriba",
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { onDirectionChange(Direction.LEFT) },
                modifier = Modifier.size(64.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("file:///android_asset/games/snake/arrow_left.png"),
                    contentDescription = "Izquierda",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(64.dp))

            IconButton(
                onClick = { onDirectionChange(Direction.RIGHT) },
                modifier = Modifier.size(64.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("file:///android_asset/games/snake/arrow_right.png"),
                    contentDescription = "Derecha",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        IconButton(
            onClick = { onDirectionChange(Direction.DOWN) },
            modifier = Modifier.size(64.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter("file:///android_asset/games/snake/arrow_down.png"),
                contentDescription = "Abajo",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
