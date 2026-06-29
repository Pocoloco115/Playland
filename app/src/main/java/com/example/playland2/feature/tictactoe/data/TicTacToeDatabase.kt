package com.example.playland2.feature.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicTacToeScoreEntity::class], version = 1, exportSchema = false)
abstract class TicTacToeDatabase : RoomDatabase() {
    abstract fun ticTacToeScoreDao(): TicTacToeScoreDao

    companion object {
        @Volatile
        private var instance: TicTacToeDatabase? = null

        fun getInstance(context: Context): TicTacToeDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                TicTacToeDatabase::class.java,
                "tictactoe_database"
            ).build().also { instance = it }
        }
    }
}
