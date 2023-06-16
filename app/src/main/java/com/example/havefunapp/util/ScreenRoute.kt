package com.example.havefunapp.util

sealed class ScreenRoute(
    val route:String
){
    object LoginScreen: ScreenRoute("login")
    object SplashScreen: ScreenRoute("splash")
    object SignupScreen: ScreenRoute("signup")
    object HomeScreen: ScreenRoute("home")
    object SecondScreen: ScreenRoute("home")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach {args->
                append("/$args")
            }
        }
    }

}
