package com.example.playland2.feature.catchfood.ui.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CatchFoodGame(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    // IMAGEN TITULO
    val imagenArriba = remember {

        try {

            context.assets.open("games/catchfood/sprites/titulo.png")
                .use { inputStream ->

                    BitmapFactory.decodeStream(inputStream).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    // IMAGEN ARRIBA DERECHA
    val imagenDerecha = remember {

        try {

            context.assets.open("games/catchfood/sprites/btn_pausa.png")
                .use { inputStream ->

                    BitmapFactory.decodeStream(inputStream).asImageBitmap()
                }

        } catch (e: Exception) {

            null
        }
    }

    Box(
        modifier = Modifier
            .size(
                width = 400.dp,
                height = 700.dp
            )
            .background(Color(0xFFFCF5E4))
    ) {

        // TITULO
        imagenArriba?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = "Imagen superior",

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

        // IMAGEN ARRIBA DERECHA
        imagenDerecha?.let { bitmap ->

            Image(
                bitmap = bitmap,
                contentDescription = "Icono derecha",

                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x = (0).dp,
                        y = 160.dp
                    )
                    .size(
                        width = 80.dp,
                        height = 80.dp
                    ),

                contentScale = ContentScale.Fit
            )
        }

        // BOTON SOBRE LA IMAGEN
        Button(
            onClick = {

                onBack()
            },

            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(
                    x = (0).dp,
                    y = 160.dp
                )
                .size(
                    width = 80.dp,
                    height = 80.dp
                ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
        }
    }
}