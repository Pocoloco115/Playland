package com.example.playland2.feature.catchfood.ui.screen

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playland2.R

@Composable
fun CatchFoodGame(onBack: () -> Unit) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val gameViewModel: CatchFoodViewModel = viewModel()
    val gameLogic = gameViewModel.gameLogic
    val uiState = gameViewModel.uiState

    val bitmaps = remember(context, density) {
        CatchFoodBitmaps.load(context, density)
    }

    LaunchedEffect(gameViewModel) {
        var previousFrameNanos = 0L
        while (true) {
            withFrameNanos { frameNanos ->
                if (!gameViewModel.uiState.isPaused && !gameViewModel.uiState.isGameOver) {
                    if (previousFrameNanos != 0L) {
                        val deltaSeconds = (frameNanos - previousFrameNanos) / 1_000_000_000f
                        gameViewModel.updateGame(deltaSeconds)
                    }
                    previousFrameNanos = frameNanos
                } else {
                    previousFrameNanos = 0L
                }
            }
        }
    }

    val gameFont = remember { FontFamily(Font(R.font.fredoka_bold)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCF5E4))
    ) {
        GameScene(
            gameLogic = gameLogic,
            onMovePlayer = gameViewModel::movePlayer,
            onViewportHeightChanged = gameViewModel::setViewportHeight,
            foodBitmap = bitmaps.food,
            poisonBitmap = bitmaps.poison,
            playerBitmap = when (uiState.playerState) {
                "hurt" -> bitmaps.hurtPlayer
                "dead" -> bitmaps.deadPlayer
                else -> bitmaps.player
            }
        )

        bitmaps.title?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-10).dp)
                    .size(width = 300.dp, height = 180.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 20.dp, y = 190.dp)
        ) {
            Text("Comida: ${uiState.score}", fontFamily = gameFont, color = Color.Black)
            Text("Veneno: ${uiState.poisonHits}/3", fontFamily = gameFont, color = Color.Red)
            Text("Perdidas: ${uiState.missedFood}/5", fontFamily = gameFont, color = Color.DarkGray)
        }

        ImageButton(
            bitmap = bitmaps.pause,
            onClick = gameViewModel::pause,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = 160.dp)
                .size(90.dp)
        )

        if (uiState.isGameOver) {
            Box(Modifier.fillMaxSize().background(Color(0xFFFCF5E4)))

            bitmaps.gameOver?.let { gameOverBitmap ->
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-60).dp)
                        .size(320.dp)
                ) {
                    Image(
                        bitmap = gameOverBitmap,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                    TransparentButton(
                        onClick = gameViewModel::restart,
                        modifier = Modifier
                            .offset(x = 12.dp, y = 190.dp)
                            .size(width = 142.dp, height = 45.dp)
                    )
                    TransparentButton(
                        onClick = onBack,
                        modifier = Modifier
                            .offset(x = 167.dp, y = 190.dp)
                            .size(width = 141.dp, height = 45.dp)
                    )
                }
            }
        }

        if (uiState.isPaused) {
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)))

            ImageButton(
                bitmap = bitmaps.continueButton,
                onClick = gameViewModel::resume,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-90).dp)
                    .size(width = 340.dp, height = 120.dp)
            )
            ImageButton(
                bitmap = bitmaps.restart,
                onClick = gameViewModel::restart,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 10.dp)
                    .size(width = 390.dp, height = 150.dp)
            )
            ImageButton(
                bitmap = bitmaps.back,
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 110.dp)
                    .size(width = 340.dp, height = 120.dp)
            )
        }
    }
}

@Composable
private fun GameScene(
    gameLogic: CatchFoodGameLogic,
    onMovePlayer: (Float) -> Unit,
    onViewportHeightChanged: (Float) -> Unit,
    foodBitmap: ImageBitmap?,
    poisonBitmap: ImageBitmap?,
    playerBitmap: ImageBitmap?
) {
    val density = LocalDensity.current.density

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(gameLogic) {
                detectDragGestures { _, dragAmount ->
                    onMovePlayer(dragAmount.x)
                }
            }
    ) {
        gameLogic.frameVersion
        onViewportHeightChanged(size.height / density)

        val objectSize = (90f * density).toInt()
        gameLogic.objects.forEach { obj ->
            val bitmap = if (obj.isPoison) poisonBitmap else foodBitmap
            bitmap?.let {
                drawImage(
                    image = it,
                    dstOffset = IntOffset(
                        x = ((obj.x / 3f) * density).toInt(),
                        y = (((obj.y / 3f) + 220f) * density).toInt()
                    ),
                    dstSize = IntSize(objectSize, objectSize)
                )
            }
        }

        playerBitmap?.let {
            val playerSize = (170f * density).toInt()
            drawImage(
                image = it,
                dstOffset = IntOffset(
                    x = ((gameLogic.playerX / 3f) * density).toInt(),
                    y = size.height.toInt() - playerSize - (40f * density).toInt()
                ),
                dstSize = IntSize(playerSize, playerSize)
            )
        }
    }
}

@Composable
private fun ImageButton(
    bitmap: ImageBitmap?,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(modifier) {
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {}
    }
}

@Composable
private fun TransparentButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
    ) {}
}

