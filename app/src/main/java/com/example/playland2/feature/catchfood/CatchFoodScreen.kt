package com.example.playland2.feature.catchfood

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.example.playland2.feature.catchfood.ui.audio.CatchFoodAudioManager

@Composable
fun CatchFoodScreen(onBack: () -> Unit) {
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
            context.assets.open("games/catchfood/sprites/background.png").use { inputStream ->
                BitmapFactory.decodeStream(inputStream).asImageBitmap()
            }
        } catch (e: Exception) {
            null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF5E4)),
        contentAlignment = Alignment.Center
    ) {
        fondoCompleto?.let { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = "Fondo de CatchFood",
                modifier = Modifier.fillMaxSize(),
                // FillBounds estira la imagen a todas las direcciones
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )
        }
    }
}