package com.example.havefunapp.model

import androidx.lifecycle.ViewModel

data class ButtonTop(
    val title: String,
    val linkTo:String,
    var isClick: Boolean = false
)