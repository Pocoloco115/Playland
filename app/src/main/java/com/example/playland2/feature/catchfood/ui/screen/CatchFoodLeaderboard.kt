package com.example.playland2.feature.catchfood.ui.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playland2.R
import com.example.playland2.feature.catchfood.data.CatchFoodScoreRepository

@Composable
fun CatchFoodLeaderboard(onBack: () -> Unit) {
    val context = LocalContext.current
    val scoreRepository = remember(context) { CatchFoodScoreRepository(context) }
    val scores by produceState(initialValue = emptyList(), scoreRepository) {
        value = scoreRepository.getTopScores()
    }
    val gameFont = remember { FontFamily(Font(R.font.fredoka_bold)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF5E4))
            .padding(horizontal = 32.dp, vertical = 48.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOP 10",
                fontFamily = gameFont,
                fontSize = 42.sp,
                color = Color(0xFFE96E9D)
            )
            Text(
                text = "Puntaje Maximo",
                fontFamily = gameFont,
                fontSize = 22.sp,
                color = Color(0xFF6B3F2D)
            )

            Spacer(Modifier.height(28.dp))

            if (scores.isEmpty()) {
                Text(
                    text = "No hay partidas.",
                    fontFamily = gameFont,
                    fontSize = 20.sp,
                    color = Color.DarkGray
                )
                Spacer(Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(scores) { index, score ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = if (index == 0) Color(0xFFFFE2A8) else Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                )
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${index + 1}.",
                                fontFamily = gameFont,
                                fontSize = 22.sp,
                                color = Color(0xFF6B3F2D)
                            )
                            Spacer(Modifier.width(20.dp))
                            Text(
                                text = score.toString(),
                                fontFamily = gameFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color(0xFF4F8D3A)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD95B55)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("VOLVER", fontFamily = gameFont, fontSize = 20.sp)
            }
        }
    }
}
