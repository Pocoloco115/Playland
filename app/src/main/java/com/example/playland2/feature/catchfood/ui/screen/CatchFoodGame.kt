package com.example.playland2.feature.catchfood.ui.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CatchFoodGame(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val gameLogic = remember {

        CatchFoodGameLogic()
    }

    // GAME LOOP
    LaunchedEffect(Unit) {

        while (true) {

            gameLogic.updateGame()

            delay(16)
        }
    }

    // PLAYER IMAGE
    val playerBitmap = remember(gameLogic.playerState) {

        try {

            val imagePath = when (gameLogic.playerState) {

                "hurt" -> "games/catchfood/sprites/enfermo.png"

                "dead" -> "games/catchfood/sprites/muerto.png"

                else -> "games/catchfood/sprites/comer.png"
            }

            context.assets.open(imagePath)
                .use {

                    BitmapFactory.decodeStream(it).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    // FOOD IMAGE
    val foodBitmap = remember {

        try {

            context.assets.open("games/catchfood/sprites/zanahoria.png")
                .use {

                    BitmapFactory.decodeStream(it).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    // POISON IMAGE
    val poisonBitmap = remember {

        try {

            context.assets.open("games/catchfood/sprites/veneno.png")
                .use {

                    BitmapFactory.decodeStream(it).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    // TITLE IMAGE
    val imagenArriba = remember {

        try {

            context.assets.open("games/catchfood/sprites/titulo.png")
                .use {

                    BitmapFactory.decodeStream(it).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    // PAUSE IMAGE
    val pauseBitmap = remember {

        try {

            context.assets.open("games/catchfood/sprites/btn_pausa.png")
                .use {

                    BitmapFactory.decodeStream(it).asImageBitmap()
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

        // TITULO
        imagenArriba?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = null,

                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(
                        x = 0.dp,
                        y = -10.dp
                    )
                    .size(
                        width = 300.dp,
                        height = 180.dp
                    ),

                contentScale = ContentScale.Fit
            )
        }

        // CONTADORES
        Column(

            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = 20.dp,
                    y = 170.dp
                )
        ) {

            Text(
                text = "Comida: ${gameLogic.score}",
                color = Color.Black
            )

            Text(
                text = "Veneno: ${gameLogic.poisonHits}/3",
                color = Color.Red
            )
        }

        // OBJECTOS CAYENDO
        gameLogic.objects.forEach { obj ->

            val bitmap = if (obj.isPoison) {

                poisonBitmap

            } else {

                foodBitmap
            }

            bitmap?.let {

                Image(
                    bitmap = it,
                    contentDescription = null,

                    modifier = Modifier
                        .offset(
                            x = (obj.x / 3).dp,
                            y = (obj.y / 3).dp
                        )
                        .size(90.dp),

                    contentScale = ContentScale.Fit
                )
            }
        }

        // PLAYER
        playerBitmap?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = null,

                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(
                        x = (gameLogic.playerX / 3).dp,
                        y = (-40).dp
                    )
                    .size(170.dp)
                    .pointerInput(Unit) {

                        detectDragGestures { _, dragAmount ->

                            gameLogic.playerX += dragAmount.x

                            // LIMITES
                            if (gameLogic.playerX < 0f) {

                                gameLogic.playerX = 0f
                            }

                            if (gameLogic.playerX > 850f) {

                                gameLogic.playerX = 850f
                            }
                        }
                    },

                contentScale = ContentScale.Fit
            )
        }

        // PAUSE IMAGE
        pauseBitmap?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = null,

                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 0.dp,
                        y = 160.dp
                    )
                    .size(80.dp)
            )
        }

        // PAUSE BUTTON
        Button(
            onClick = {

                onBack()
            },

            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(
                    x = 0.dp,
                    y = 160.dp
                )
                .size(80.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }

        // GAME OVER
        if (gameLogic.isGameOver) {

            Text(

                text = "GAME OVER",

                modifier = Modifier
                    .align(Alignment.Center),

                color = Color.Red
            )
        }
    }
}