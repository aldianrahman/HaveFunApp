package com.example.havefunapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.db.AppDatabase
import androidx.compose.runtime.getValue



import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.screen.SplashScreen
import com.example.havefunapp.screen.loginScreen
import com.example.havefunapp.screen.signupScreen
import com.example.havefunapp.transport.IonMaster
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.ButtonBlue
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.LightRed
import com.example.havefunapp.ui.theme.MeditationUIYouTubeTheme
import com.example.havefunapp.ui.theme.TextWhite
import com.google.gson.JsonObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeditationUIYouTubeTheme{

            val context = LocalContext.current
            val db: UserDao = AppDatabase.getInstance(context)?.userDao()!!
            refeshDB(context,db)

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {

                    composable(ScreenRoute.SplashScreen.route) {
                        refeshDB(context,db)
                        SplashScreen(context) { navController.navigate(ScreenRoute.SignupScreen.route) }
                    }

                    composable(ScreenRoute.SignupScreen.route){
                        refeshDB(context,db)
                        signupScreen(context,navController,db)
                    }

                    composable(ScreenRoute.LoginScreen.route){
                        refeshDB(context,db)
                        loginScreen(context,db,navController)
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
                                val email = jsonObject.get("email").asString

                                db.insertOrReplaceUser(
                                    idUser =userId,
                                    userName = username,
                                    password = password,
                                    email = email

                                )
                            }
                        }
                    }
                }

            })
        }




    }

    @Composable
    fun defaultButtonColor(): ButtonColors {

        return ButtonDefaults.buttonColors(
            containerColor = ButtonBlue, // Ubah warna latar belakang
        )
    }

    @Composable
    fun BackPressHandler(
        backPressedDispatcher: OnBackPressedDispatcher? =
            LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
        onBackPressed: () -> Unit
    ) {
        val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBackPressed()
                }
            }
        }

        DisposableEffect(key1 = backPressedDispatcher) {
            backPressedDispatcher?.addCallback(backCallback)

            onDispose {
                backCallback.remove()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun defaultTextFieldColor(): TextFieldColors {
        return TextFieldDefaults.textFieldColors(
            containerColor = TextWhite, // Ubah warna latar belakang
            cursorColor = DeepBlue, // Ubah warna kursor
            textColor = DeepBlue, // Ubah warna teks
            focusedIndicatorColor = LightRed, // Ubah warna indikator saat fokus
            unfocusedIndicatorColor = DeepBlue // Ubah warna indikator saat tidak fokus
        )
    }


}


