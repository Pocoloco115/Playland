package com.example.playland2.feature.catchfood

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.playland2.feature.catchfood.ui.audio.CatchFoodAudioManager

@Composable
fun CatchFoodScreen(onBack: () -> Unit, onGoToGame: () -> Unit) {

    val context = LocalContext.current
    val audioManager = remember { CatchFoodAudioManager(context) }

    // LifeCycle de married life
    DisposableEffect(Unit) {

        audioManager.startMusic()

        onDispose {
            audioManager.stopMusic()
        }
    }

    val fondoCompleto = remember {

        try {

            context.assets.open("games/catchfood/sprites/background.png")
                .use { inputStream ->

                    BitmapFactory.decodeStream(inputStream).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF5E4))
    ) {

        fondoCompleto?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = "Fondo de CatchFood",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )
        }


        Button(
            onClick = { onGoToGame()},

            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 0.dp,
                    y = 25.dp
                )
                .size(
                    width = 260.dp,
                    height = 70.dp
                ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }

        Button(
            onClick = { },

            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 0.dp,
                    y = 140.dp
                )
                .size(
                    width = 260.dp,
                    height = 70.dp
                ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }

        Button(
            onClick = { onBack() },

            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 0.dp,
                    y = 240.dp
                )
                .size(
                    width = 260.dp,
                    height = 70.dp
                ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }
    }
}