package com.example.playland2.feature.flappybird

data class Pipe(
    var x: Float,
    val gapY: Float,
    val gapHeight: Float,
    val width: Float,

    var passed: Boolean = false
)