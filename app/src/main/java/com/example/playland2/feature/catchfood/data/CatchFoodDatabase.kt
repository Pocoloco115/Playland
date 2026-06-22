package com.example.playland2.feature.catchfood.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CatchFoodScoreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatchFoodDatabase : RoomDatabase() {
    abstract fun scoreDao(): CatchFoodScoreDao

    companion object {
        @Volatile
        private var instance: CatchFoodDatabase? = null

        fun getInstance(context: Context): CatchFoodDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                CatchFoodDatabase::class.java,
                DATABASE_NAME
            ).build().also { instance = it }
        }

        private const val DATABASE_NAME = "catch_food.db"
    }
}
