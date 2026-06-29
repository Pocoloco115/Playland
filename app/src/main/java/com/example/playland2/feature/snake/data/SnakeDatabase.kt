package com.example.playland2.feature.snake.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SnakeScoreEntity::class], version = 1, exportSchema = false)
abstract class SnakeDatabase : RoomDatabase() {
    abstract fun snakeScoreDao(): SnakeScoreDao

    companion object {
        @Volatile
        private var instance: SnakeDatabase? = null

        fun getInstance(context: Context): SnakeDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                SnakeDatabase::class.java,
                "snake_database"
            ).build().also { instance = it }
        }
    }
}
