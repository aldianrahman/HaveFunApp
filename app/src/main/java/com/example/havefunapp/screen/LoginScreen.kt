package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun loginScreen(
    context: Context,
    editor: SharedPreferences.Editor,
    db: UserDao,
    navController: NavHostController
) {

    val paddingUp: Modifier = Modifier.padding(bottom = 8.dp)
    val paddingDown: Modifier = Modifier.padding(bottom = 16.dp)

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
    var rememberMe by remember { mutableStateOf(false) }



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
            Image(
                painter = painterResource( R.drawable.ic_launcher_background), // Ubah "logo.png" dengan path ke file logo Anda
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp).padding(bottom = 16.dp)
            )

            Text(Util.appName, style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { newUser -> email = newUser },
                placeholder = { Text("Enter your Email") },
                modifier = paddingUp,
                colors = mainActivity.defaultTextFieldColor()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                placeholder = { Text("Enter your password") },
                modifier = paddingDown,
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Ingat Saya")
            }
            Button(
                onClick = {

                    val encryptedPassword = encryptMD5(password)

                    val checkLogin = db.getUserByUsernameAndPassword(email,encryptedPassword)
                    Log.i("DB_LOGIN", "loginScreen: $checkLogin")

                    if(email == ""){
                        Util.toastToText(context,"Masukan User Anda")
                    }else if (password == ""){
                        Util.toastToText(context, "Masukan Password Anda")
                    }else if(checkLogin) {

                        editor.putBoolean(Util.RememberME,rememberMe)
                        editor.putString(Util.idlUser,db.getUserIdByEmail(email))
                        editor.putString(Util.emailUser,email)
                        editor.putString(Util.nameUser,db.getUsernameByEmail(email))
                        editor.apply()
                        val result = editor.commit()


                        if (result) {
                            Util.toastToText(context,"Login Berhasil")
                            navController.navigate(ScreenRoute.HomeScreen.route)
                        } else {
                            Util.toastToText(context, "Gagal menerapkan perubahan")
                        }
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