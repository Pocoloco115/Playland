package com.example.playland2.feature.catchfood.ui.screen

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

data class CatchFoodBitmaps(
    val player: ImageBitmap?,
    val hurtPlayer: ImageBitmap?,
    val deadPlayer: ImageBitmap?,
    val food: ImageBitmap?,
    val poison: ImageBitmap?,
    val title: ImageBitmap?,
    val pause: ImageBitmap?,
    val continueButton: ImageBitmap?,
    val restart: ImageBitmap?,
    val back: ImageBitmap?,
    val gameOver: ImageBitmap?
) {
    companion object {
        fun load(context: Context, density: Float): CatchFoodBitmaps {
            fun load(path: String, widthDp: Int, heightDp: Int) =
                decodeSampledAsset(context, path, widthDp * density, heightDp * density)

            return CatchFoodBitmaps(
                player = load("games/catchfood/sprites/comer.png", 170, 170),
                hurtPlayer = load("games/catchfood/sprites/enfermo.png", 170, 170),
                deadPlayer = load("games/catchfood/sprites/muerto.png", 170, 170),
                food = load("games/catchfood/sprites/zanahoria.png", 90, 90),
                poison = load("games/catchfood/sprites/veneno.png", 90, 90),
                title = load("games/catchfood/sprites/titulo.png", 300, 180),
                pause = load("games/catchfood/sprites/btn_pausa.png", 90, 90),
                continueButton = load("games/catchfood/sprites/btn_continuar.png", 340, 120),
                restart = load("games/catchfood/sprites/btn_reiniciar.png", 390, 150),
                back = load("games/catchfood/sprites/btn_volver_menu_principal.png", 340, 120),
                gameOver = load("games/catchfood/sprites/screen_game_lost.png", 320, 320)
            )
        }
    }
}

private fun decodeSampledAsset(
    context: Context,
    path: String,
    requestedWidthPx: Float,
    requestedHeightPx: Float
): ImageBitmap? = runCatching {
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    context.assets.open(path).use { BitmapFactory.decodeStream(it, null, bounds) }

    val fitScale = minOf(
        requestedWidthPx / bounds.outWidth,
        requestedHeightPx / bounds.outHeight
    )
    val fittedWidth = bounds.outWidth * fitScale
    val fittedHeight = bounds.outHeight * fitScale

    var sampleSize = 1
    while (
        bounds.outWidth / (sampleSize * 2) >= fittedWidth &&
        bounds.outHeight / (sampleSize * 2) >= fittedHeight
    ) {
        sampleSize *= 2
    }

    val options = BitmapFactory.Options().apply {
        inSampleSize = sampleSize
        inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888
    }
    context.assets.open(path).use { stream ->
        requireNotNull(BitmapFactory.decodeStream(stream, null, options)).asImageBitmap()
    }
}.getOrNull()
