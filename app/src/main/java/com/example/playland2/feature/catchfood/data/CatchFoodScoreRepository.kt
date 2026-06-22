package com.example.playland2.feature.catchfood.data

import android.content.Context

class CatchFoodScoreRepository(context: Context) {
    private val scoreDao = CatchFoodDatabase.getInstance(context).scoreDao()

    suspend fun saveScore(score: Int) {
        scoreDao.insert(CatchFoodScoreEntity(foodCaught = score))
    }

    suspend fun getTopScores(): List<Int> = scoreDao.getTopTenScores()
}
