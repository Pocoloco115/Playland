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

        //con esta funcion me traigo la bsd
        fun getInstance(context: Context): CatchFoodDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                CatchFoodDatabase::class.java,
                bsd_name
            ).build().also { instance = it }
        }

        private const val bsd_name = "catch_food.db"
    }
}
