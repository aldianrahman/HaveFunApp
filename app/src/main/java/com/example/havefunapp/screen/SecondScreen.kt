package com.example.havefunapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.havefunapp.R
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.util.BottomMenuContent

@Composable
fun SecondScreen(index: String?) {
    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxSize()){
        if (index != null) {
            BottomMenu(
                editor = null,
                items = listOf(
                    BottomMenuContent("Home", R.drawable.ic_home),
                    BottomMenuContent("Meditate", R.drawable.ic_bubble),
                    BottomMenuContent("Sleep", R.drawable.ic_moon),
                    BottomMenuContent("Music", R.drawable.ic_music),
                    BottomMenuContent("Profile", R.drawable.ic_profile),
                ), modifier = Modifier.align(Alignment.BottomCenter)
                , navController = null,
                initialSelectedItemIndex = index.toInt()
            )
        }
    }
}