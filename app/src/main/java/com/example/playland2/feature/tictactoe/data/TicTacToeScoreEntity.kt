package com.example.playland2.feature.tictactoe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tictactoe_scores")
data class TicTacToeScoreEntity(
    @PrimaryKey val id: Int = 1,
    val xWins: Int = 0,
    val oWins: Int = 0,
    val draws: Int = 0
)
