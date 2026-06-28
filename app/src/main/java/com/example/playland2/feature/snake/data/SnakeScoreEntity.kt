package com.example.playland2.feature.snake.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "snake_scores")
data class SnakeScoreEntity(
    @PrimaryKey val id: Int = 1,
    val highScore: Int
)
