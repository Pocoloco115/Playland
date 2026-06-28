package com.example.playland2.feature.snake.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SnakeRepository(context: Context) {
    private val dao = SnakeDatabase.getInstance(context).snakeScoreDao()

    fun getHighScore(): Flow<Int> {
        return dao.getHighScore().map { it?.highScore ?: 0 }
    }

    suspend fun saveHighScore(score: Int) {
        dao.insertScore(SnakeScoreEntity(id = 1, highScore = score))
    }
}
