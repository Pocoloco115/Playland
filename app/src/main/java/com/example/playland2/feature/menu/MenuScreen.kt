package com.example.playland2.feature.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playland2.R

@Composable
fun MenuScreen(
    onNavigateToSnake: () -> Unit,
    onNavigateToTicTacToe: () -> Unit,
    onNavigateToCatchFood: () -> Unit,
    onNavigateToFlappyBird: () -> Unit
) {
    val gameFont = remember { FontFamily(Font(R.font.fredoka_bold)) }
    val games = listOf(
        MenuGame("Snake", "Desliza y crece", "S", Color(0xFF4F8D3A), onNavigateToSnake),
        MenuGame("Flappy Bird", "Vuela sin caer", "F", Color(0xFF3F7CAC), onNavigateToFlappyBird),
        MenuGame("Catch Food", "Atrapa comida", "C", Color(0xFFE96E9D), onNavigateToCatchFood),
        MenuGame("Tic Tac Toe", "Tres en raya", "T", Color(0xFFD95B55), onNavigateToTicTacToe)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF5E4))
            .padding(horizontal = 28.dp, vertical = 44.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PLAYLAND",
                fontFamily = gameFont,
                fontSize = 46.sp,
                color = Color(0xFFE96E9D)
            )
            Text(
                text = "Menu de juegos",
                fontFamily = gameFont,
                fontSize = 22.sp,
                color = Color(0xFF6B3F2D)
            )

            Spacer(modifier = Modifier.height(34.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                games.forEach { game ->
                    GameMenuButton(game = game, gameFont = gameFont)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Elige tu partida",
                fontFamily = gameFont,
                fontSize = 18.sp,
                color = Color(0xFF6B3F2D)
            )
        }
    }
}

@Composable
private fun GameMenuButton(
    game: MenuGame,
    gameFont: FontFamily
) {
    ElevatedButton(
        onClick = game.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF6B3F2D)
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(46.dp)
                    .background(game.color, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.initial,
                    fontFamily = gameFont,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = game.title,
                    fontFamily = gameFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF6B3F2D)
                )
                Text(
                    text = game.subtitle,
                    fontFamily = gameFont,
                    fontSize = 14.sp,
                    color = Color(0xFF8B6B56)
                )
            }

            Text(
                text = ">",
                fontFamily = gameFont,
                fontSize = 28.sp,
                color = game.color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = game.title,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

private data class MenuGame(
    val title: String,
    val subtitle: String,
    val initial: String,
    val color: Color,
    val onClick: () -> Unit
)
