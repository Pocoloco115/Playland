package com.example.playland2.feature.tictactoe.data

import android.content.Context
import android.content.SharedPreferences

class TicTacToePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("tictactoe_prefs", Context.MODE_PRIVATE)

    fun saveScores(xWins: Int, oWins: Int, draws: Int) {
        sharedPreferences.edit().apply {
            putInt("x_wins", xWins)
            putInt("o_wins", oWins)
            putInt("draws", draws)
            apply()
        }
    }

    fun getXWins(): Int = sharedPreferences.getInt("x_wins", 0)
    fun getOWins(): Int = sharedPreferences.getInt("o_wins", 0)
    fun getDraws(): Int = sharedPreferences.getInt("draws", 0)
    
    fun clearScores() {
        sharedPreferences.edit().clear().apply()
    }
}
