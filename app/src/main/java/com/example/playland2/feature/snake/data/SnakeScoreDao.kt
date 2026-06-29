package com.example.playland2.feature.snake.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SnakeScoreDao {
    @Query("SELECT * FROM snake_scores WHERE id = 1")
    fun getHighScore(): Flow<SnakeScoreEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: SnakeScoreEntity)
}
