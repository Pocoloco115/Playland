package com.example.playland2.feature.catchfood.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//aqui defino la tabla y las columnas de la table de record
@Entity(tableName = "catch_food_scores")
data class CatchFoodScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val foodCaught: Int
)
