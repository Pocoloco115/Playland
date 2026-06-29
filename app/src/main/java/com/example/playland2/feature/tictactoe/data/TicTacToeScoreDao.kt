package com.example.playland2.feature.tictactoe.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TicTacToeScoreDao {
    @Query("SELECT * FROM tictactoe_scores WHERE id = 1")
    fun getScore(): Flow<TicTacToeScoreEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: TicTacToeScoreEntity)

    @Query("DELETE FROM tictactoe_scores")
    suspend fun clearScores()
}
