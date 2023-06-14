package com.example.havefunapp.util

sealed class ScreenRoute(
    val route:String
){
    object LoginScreen: ScreenRoute("login")
    object SplashScreen: ScreenRoute("splash")
    object SignupScreen: ScreenRoute("signup")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach {args->
                append("/$args")
            }
        }
    }

}
