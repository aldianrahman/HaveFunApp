package com.example.havefunapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.screen.SplashScreen
import com.example.havefunapp.screen.loginScreen
import com.example.havefunapp.screen.signupScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {

                    composable(ScreenRoute.SplashScreen.route) {
                        SplashScreen(navigateToNextScreen = { navController.navigate(ScreenRoute.SignupScreen.route) })
                    }

                    composable(ScreenRoute.LoginScreen.route){
                        loginScreen()
                    }

                    composable(ScreenRoute.SignupScreen.route){
                        signupScreen(navController)
                    }

                }
            }




        }
    }
}


