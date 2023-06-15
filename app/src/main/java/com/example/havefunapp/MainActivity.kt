package com.example.havefunapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.db.AppDatabase
import com.example.havefunapp.entity.Users

import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.screen.SplashScreen
import com.example.havefunapp.screen.loginScreen
import com.example.havefunapp.screen.signupScreen
import com.example.havefunapp.transport.IonMaster
import com.example.havefunapp.transport.MainTransport
import com.google.gson.JsonObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val db: UserDao = AppDatabase.getInstance(context)?.userDao()!!

            refeshDB(context,db)

            MaterialTheme{
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {

                    composable(ScreenRoute.SplashScreen.route) {
                        refeshDB(context,db)
                        SplashScreen() { navController.navigate(ScreenRoute.SignupScreen.route) }
                    }

                    composable(ScreenRoute.SignupScreen.route){
                        refeshDB(context,db)
                        signupScreen(context,navController,db)
                    }

                    composable(ScreenRoute.LoginScreen.route){
                        refeshDB(context,db)
                        loginScreen(context,db)
                    }



                }
            }




        }
    }

    @Composable
    fun refeshDB(context: Context, db: UserDao) {

        LaunchedEffect(key1 = true){
            db.deleteAllUsers()
            val mainTransport= MainTransport()
            val TAG = "GetDataLogin"

            mainTransport.getData(context,object : IonMaster.IonCallback {
                override fun onReadyCallback(errorMessage: String?, `object`: Any?) {
                    var result = `object`
                    if (errorMessage != null) {
                        Log.i(TAG, "getApiMongodb Error: $errorMessage")
                    } else {
                        Log.i(TAG, "getApiMongodb Success: $result")
                        if (result != null) {
                            result as JsonObject
                            val document = result.getAsJsonObject("document")
                            val user = document.getAsJsonArray("user")

                            for (i in 0 until user.size()) {
                                val jsonObject = user[i].asJsonObject
                                val username = jsonObject.get("userName").asString
                                val password = jsonObject.get("password").asString
                                val userId = jsonObject.get("userId").asString

                                db.insertOrReplaceUser(
                                    Users(
                                        userName = username,
                                        password = password
                                    )
                                )
                            }
                        }
                    }
                }

            })
        }




    }


}


