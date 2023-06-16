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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.havefunapp.screen.HomeScreen
import com.example.havefunapp.screen.SecondScreen


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
import com.example.havefunapp.util.Util
import com.google.gson.JsonObject
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val sharedPreferences = getSharedPreferences(Util.NamePref, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val isLogin = sharedPreferences.getBoolean(Util.RememberME,false)





            MeditationUIYouTubeTheme{

                val context = LocalContext.current


                val TAG: String = "JSON_MONGODB"
                var mainTransport = MainTransport()
                var names = ""
                var email = ""
                val stringButton = mutableListOf<String>()
                val stringFeature = mutableListOf<String>()

                stringButton.add("Button 1")
                stringButton.add("Button 2")
                stringButton.add("Button 3")

                // Populate stringFeature list
                stringFeature.add("Feature 1")
                stringFeature.add("Feature 2")
                stringFeature.add("Feature 3")
                stringFeature.add("Feature 4")
                stringFeature.add("Feature 5")
                stringFeature.add("Feature 6")



//                mainTransport.getData(context, object : IonMaster.IonCallback {
//                    override fun onReadyCallback(errorMessage: String?, `object`: Any?) {
//                        var result = `object`
//                        if (errorMessage != null) {
//                            Log.i(TAG, "getApiMongodb Error: $errorMessage")
//                        } else {
//                            Log.i(TAG, "getApiMongodb Success: $result")
//                            if (result != null) {
//                                result as JsonObject
//                                val document = result.getAsJsonObject("document")
//                                val data_user = document.getAsJsonObject("data_user")
//                                val buttonString = document.getAsJsonArray("listButton")
//                                val featureString = document.getAsJsonArray("listFeature")
//                                names = data_user["name"].asString
//                                email = data_user["email"].asString
//                                Log.i(TAG, "Solution: $names")
//                                Log.i(TAG, "Button String: $buttonString")
//
//                                for (i in 0 until buttonString.size()) {
//                                    val button = buttonString.get(i).asString
//                                    stringButton.add(button)
//                                    Log.d("Button", button)
//                                }
//
//                                for (i in 0 until featureString.size()) {
//                                    val feature = featureString.get(i).asString
//                                    stringFeature.add(feature)
//                                    Log.d("Feature", feature)
//                                }
//                            }
//                        }
//                    }
//                })

                val db: UserDao = AppDatabase.getInstance(context)?.userDao()!!
                refeshDB(context,db)
                val calendar = Calendar.getInstance()
                val date = calendar.get(Calendar.DATE)

                val dayOfWeekString = when (calendar.get(Calendar.DAY_OF_WEEK)) {
                    Calendar.SUNDAY -> "Sunday"
                    Calendar.MONDAY -> "Monday"
                    Calendar.TUESDAY -> "Tuesday"
                    Calendar.WEDNESDAY -> "Wednesday"
                    Calendar.THURSDAY -> "Thursday"
                    Calendar.FRIDAY -> "Friday"
                    Calendar.SATURDAY -> "Saturday"
                    else -> "Unknown"
                }
                val monthNames = arrayOf(
                    "January", "February", "March", "April",
                    "May", "June", "July", "August",
                    "September", "October", "November", "December"
                )
                val month = calendar.get(Calendar.MONTH) + 1 // Note: Months start from 0
                val year = calendar.get(Calendar.YEAR)
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                var inDay = ""
                var wish = ""

                inDay = when (hour) {
                    in 6..11 -> "Morning"
                    in 12..17 -> "Afternoon"
                    else -> "Night"
                }

                wish = when (hour) {
                    in 6..11 -> "May your day bring happiness and success!"
                    in 12..13 -> "The time for rest and prayer has come!"
                    in 12..17 -> "Rediscover your motivation to get back to work!"
                    else -> "It's time for a break, relax yourself!"
                }

                val name = "Aldian Rahman"
                val salam = "Good $inDay, $name"
                val harapan = wish

                var toGo = ""

                if (isLogin){
                    toGo = ScreenRoute.HomeScreen.route
                }else{
                    toGo = ScreenRoute.SplashScreen.route
                }


                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = toGo) {



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
                        loginScreen(context,editor,db,navController)
                    }

                    composable(ScreenRoute.HomeScreen.route){
                        refeshDB(context,db)
                        HomeScreen(context,editor,salam,harapan,
                            "$dayOfWeekString, $date "+monthNames[month]+" $year",
                            names,
                            email,
                            stringButton,
                            stringFeature,
                            navController)
                    }

                    composable(
                        ScreenRoute.SecondScreen.route+ "/{index}",
                        arguments = listOf(
                            navArgument("index"){
                                type = NavType.StringType
                                defaultValue = "Error Data"
                                nullable = true
                            }
                        )
                    ){entry->
                        SecondScreen(entry.arguments?.getString("index"))
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


