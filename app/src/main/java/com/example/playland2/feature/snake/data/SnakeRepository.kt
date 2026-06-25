package com.example.playland2.feature.snake.data

import android.content.Context
import android.content.SharedPreferences

class SnakeRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("snake_prefs", Context.MODE_PRIVATE)

    fun getHighScore(): Int {
        return prefs.getInt("high_score", 0)
    }

    fun saveHighScore(score: Int) {
        val currentHigh = getHighScore()
        if (score > currentHigh) {
            prefs.edit().putInt("high_score", score).apply()
        }
    }
}
