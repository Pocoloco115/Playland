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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.playland2.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CatchFoodGame(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val gameLogic = remember {

        CatchFoodGameLogic()
    }

    var isPaused by remember {

        mutableStateOf(false)
    }

    // GAME LOOP
    LaunchedEffect(Unit) {

        while (true) {

            if (!isPaused && !gameLogic.isGameOver) {

                gameLogic.updateGame()
            }

            delay(16)
        }
    }

    // FUENTE
    val gameFont = FontFamily(

        Font(R.font.fredoka_bold)
    )

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

            context.assets.open(
                "games/catchfood/sprites/zanahoria.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // POISON IMAGE
    val poisonBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/veneno.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // TITLE IMAGE
    val imagenArriba = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/titulo.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // PAUSE IMAGE
    val pauseBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/btn_pausa.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // CONTINUAR IMAGE
    val continuarBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/btn_continuar.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // RESTART IMAGE
    val restartBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/btn_reiniciar.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // VOLVER MENU IMAGE
    val volverBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/btn_volver_menu_principal.png"
            ).use {

                BitmapFactory.decodeStream(it).asImageBitmap()
            }

        } catch (e: Exception) {

            null
        }
    }

    // GAME OVER IMAGE
    val gameOverBitmap = remember {

        try {

            context.assets.open(
                "games/catchfood/sprites/screen_game_lost.png"
            ).use {

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
                    .offset(y = (-10).dp)
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
                    y = 190.dp
                )
        ) {

            Text(
                text = "Comida: ${gameLogic.score}",
                fontFamily = gameFont,
                color = Color.Black
            )

            Text(
                text = "Veneno: ${gameLogic.poisonHits}/3",
                fontFamily = gameFont,
                color = Color.Red
            )

            Text(
                text = "Perdidas: ${gameLogic.missedFood}/5",
                fontFamily = gameFont,
                color = Color.DarkGray
            )
        }

        // OBJECTOS
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
                            y = ((obj.y / 3) + 220).dp
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
                    .offset(y = 160.dp)
                    .size(90.dp)
            )
        }

        // PAUSE BUTTON
        Button(
            onClick = {

                isPaused = true
            },

            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 160.dp)
                .size(90.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }

        // GAME OVER SCREEN
        if (gameLogic.isGameOver) {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFCF5E4))
            )

            // GAME OVER IMAGE
            gameOverBitmap?.let { bitmap ->

                Image(
                    bitmap = bitmap,
                    contentDescription = null,

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-60).dp)
                        .size(
                            width = 320.dp,
                            height = 320.dp
                        ),

                    contentScale = ContentScale.Fit
                )
            }

            // BOTON REINICIAR
            Button(
                onClick = {

                    gameLogic.objects.clear()

                    gameLogic.score = 0

                    gameLogic.poisonHits = 0

                    gameLogic.missedFood = 0

                    gameLogic.speed = 8f

                    gameLogic.playerState = "eat"

                    gameLogic.isGameOver = false

                    repeat(8) { index ->

                        gameLogic.objects.add(

                            FallingObject(

                                x = Random.nextInt(
                                    80,
                                    850
                                ).toFloat(),

                                y = (-500f * index),

                                isPoison = false
                            )
                        )
                    }

                    repeat(2) { index ->

                        gameLogic.objects.add(

                            FallingObject(

                                x = Random.nextInt(
                                    80,
                                    850
                                ).toFloat(),

                                y = (-3500f - (700f * index)),

                                isPoison = true
                            )
                        )
                    }
                },

                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(
                        x = 30.dp,
                        y = (-70).dp
                    )
                    .size(
                        width = 150.dp,
                        height = 90.dp
                    ),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
            }

            // BOTON MENU
            Button(
                onClick = {

                    onBack()
                },

                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = (-30).dp,
                        y = (-70).dp
                    )
                    .size(
                        width = 150.dp,
                        height = 90.dp
                    ),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
            }
        }

        // PAUSE MENU
        if (isPaused) {

            // FONDO OSCURO
            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(alpha = 0.5f)
                    )
            )

            // CONTINUAR
            continuarBitmap?.let { bitmap ->

                Image(
                    bitmap = bitmap,
                    contentDescription = null,

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-90).dp)
                        .size(
                            width = 340.dp,
                            height = 120.dp
                        )
                )

                Button(
                    onClick = {

                        isPaused = false
                    },

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-90).dp)
                        .size(
                            width = 340.dp,
                            height = 120.dp
                        ),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                }
            }

            // REINICIAR
            restartBitmap?.let { bitmap ->

                Image(
                    bitmap = bitmap,
                    contentDescription = null,

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 10.dp)
                        .size(
                            width = 390.dp,
                            height = 150.dp
                        )
                )

                Button(
                    onClick = {

                        gameLogic.objects.clear()

                        gameLogic.score = 0

                        gameLogic.poisonHits = 0

                        gameLogic.missedFood = 0

                        gameLogic.speed = 8f

                        gameLogic.playerState = "eat"

                        gameLogic.isGameOver = false

                        repeat(8) { index ->

                            gameLogic.objects.add(

                                FallingObject(

                                    x = Random.nextInt(
                                        80,
                                        850
                                    ).toFloat(),

                                    y = (-500f * index),

                                    isPoison = false
                                )
                            )
                        }

                        repeat(2) { index ->

                            gameLogic.objects.add(

                                FallingObject(

                                    x = Random.nextInt(
                                        80,
                                        850
                                    ).toFloat(),

                                    y = (-3500f - (700f * index)),

                                    isPoison = true
                                )
                            )
                        }

                        isPaused = false
                    },

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 10.dp)
                        .size(
                            width = 390.dp,
                            height = 150.dp
                        ),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                }
            }

            // VOLVER MENU
            volverBitmap?.let { bitmap ->

                Image(
                    bitmap = bitmap,
                    contentDescription = null,

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 110.dp)
                        .size(
                            width = 340.dp,
                            height = 120.dp
                        )
                )

                Button(
                    onClick = {

                        onBack()
                    },

                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 110.dp)
                        .size(
                            width = 340.dp,
                            height = 120.dp
                        ),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                }
            }
        }
    }
}
