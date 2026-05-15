package com.example.playland2.feature.tictactoe.domain.model

enum class Player {
    X, O;

    fun next(): Player {
        return if (this == X) O else X
    }
}