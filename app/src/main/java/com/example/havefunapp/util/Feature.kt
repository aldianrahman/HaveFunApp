package com.example.havefunapp.util


import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Feature(
    var title: String,
    var score: String,
    var release_date: String,
    var overview: String,
    var backDrop: String,
    var posterPath: String,
    @DrawableRes var iconId: Int,
    var lightColor: Color,
    var mediumColor: Color,
    var darkColor: Color
)
