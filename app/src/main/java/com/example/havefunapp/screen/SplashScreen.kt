package com.example.havefunapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.havefunapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToNextScreen: () -> Unit) {
    var expand by remember{
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        delay(1500)
        expand = !expand
        delay(3000) // Menunggu 3 detik sebelum melanjutkan ke layar berikutnya
        navigateToNextScreen()
    }

    // Tampilan SplashScreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(Color.White)
            ) {
                Image(
                    painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.background(Color.White)
                )
                AnimatedVisibility(expand){
                    Text(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Jetpack Compose",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.background(Color.White)
                    )
                }
            }
        }
    }
}