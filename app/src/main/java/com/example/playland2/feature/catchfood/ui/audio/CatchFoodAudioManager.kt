package com.example.playland2.feature.catchfood.ui.audio

import android.content.Context
import android.media.MediaPlayer

class CatchFoodAudioManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun startMusic() {
        if (mediaPlayer != null) return

        mediaPlayer = MediaPlayer().apply {
            try {
                context.assets.openFd("games/catchfood/music/married_life.mp3").use { fd ->
                    setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
                }
                isLooping = true //bucle infinito
                prepare()
                start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Para la musica y libera memoria
    fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release() // Libera el recurso del sistema operativo
        }
        mediaPlayer = null
    }
}