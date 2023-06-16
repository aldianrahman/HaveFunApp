package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.ui.theme.primaryColor
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util

@SuppressLint("PrivateResource", "ComposableNaming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(context: Context, db: UserDao, navController: NavHostController) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val mainActivity= MainActivity()
    val onBack = {
        Util.toastToText(
            context,
            "Tekan tombol 'Back' sekali lagi untuk menutup aplikasi"
        )
    }



    mainActivity.BackPressHandler(onBackPressed = onBack)

    Box(
        modifier = Modifier
            .background(primaryColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Please Login")
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            TextField(
                value = email,
                onValueChange = { newUser -> email = newUser },
                placeholder = { Text("Enter your Email") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                colors = mainActivity.defaultTextFieldColor()
            )
            TextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                colors = mainActivity.defaultTextFieldColor(),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(com.google.android.material.R.drawable.design_ic_visibility_off) else painterResource(
                                com.google.android.material.R.drawable.design_ic_visibility),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )
            Button(
                onClick = {

                    val checkLogin = db.getUserByUsernameAndPassword(email,password)
                    Log.i("DB_LOGIN", "loginScreen: $checkLogin")

                    if(email == ""){
                        Util.toastToText(context,"Masukan User Anda")
                    }else if (password == ""){
                        Util.toastToText(context, "Masukan Password Anda")
                    }else if(checkLogin) {
                        Util.toastToText(context,"Login Berhasil")
                        navController.navigate(ScreenRoute.HomeScreen.route)
                    }else{
                        Util.toastToText(context,"Login Gagal")
                    }

                },
                colors = mainActivity.defaultButtonColor()
            ){
                Text("Login",color = TextWhite)
            }
            Text("Buat akun baru mu sekarang!",
                modifier = Modifier.clickable {
                    navController.navigate(ScreenRoute.SignupScreen.route)
                }
            )
        }
    }
}