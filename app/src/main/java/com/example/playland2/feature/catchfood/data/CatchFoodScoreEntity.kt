package com.example.playland2.feature.catchfood.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catch_food_scores")
data class CatchFoodScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val foodCaught: Int
)
