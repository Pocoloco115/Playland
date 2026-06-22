package com.example.playland2.feature.catchfood.data

import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query

@Dao
interface CatchFoodScoreDao {
    @Insert
    suspend fun insert(score: CatchFoodScoreEntity)

    @Query(
        """
        SELECT foodCaught
        FROM catch_food_scores
        ORDER BY foodCaught DESC, id ASC
        LIMIT 10
        """
    )
    suspend fun getTopTenScores(): List<Int>
}
