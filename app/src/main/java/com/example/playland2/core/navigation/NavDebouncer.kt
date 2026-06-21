package com.example.playland2.core.navigation

import android.os.SystemClock

object NavDebouncer {
    private var lastClickTime = 0L
    private const val COOLDOWN = 600L

    fun process(action: () -> Unit) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > COOLDOWN) {
            lastClickTime = currentTime
            action()
        }
    }
}