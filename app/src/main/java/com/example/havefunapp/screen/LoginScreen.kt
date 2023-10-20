package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.ui.theme.primaryColor
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util
import com.example.havefunapp.viewModel.UserViewModel

@SuppressLint("PrivateResource", "ComposableNaming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(
    context: Context,
    editor: SharedPreferences.Editor,
    db: UserDao,
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel (),
    exitToApp:(Boolean)-> Unit
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

    var rememberMe by remember { mutableStateOf(false) }



    mainActivity.BackPressHandler {
        exitToApp(it)
    }

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
            CoolTextField(
                value = userViewModel.email,
                onValueChange = { newUser -> userViewModel.email = newUser },
                placeholder = "Your Email",
                modifier = paddingUp,
                colors = mainActivity.defaultTextFieldColor()
            )
            CoolTextField(
                value = userViewModel.password,
                onValueChange = { newPassword -> userViewModel.password = newPassword },
                placeholder = "Your password",
                modifier = paddingDown,
                colors = mainActivity.defaultTextFieldColor(),
                visualTransformation = userViewModel.passwordVisibility(userViewModel.passwordVisibility),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { userViewModel.passwordVisibility = !userViewModel.passwordVisibility }
                    ) {
                        Icon(
                            painter = userViewModel.passwordVisibilityIcon(userViewModel.passwordVisibility),
                            contentDescription = "Toggle Password Visibility",
                            tint = TextWhite
                        )
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = userViewModel.rememberMe,
                    onCheckedChange = { userViewModel.rememberMe = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = TextWhite,
                        checkedColor = DeepBlue,
                        uncheckedColor = TextWhite
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Ingat Saya")
            }
            Button(
                onClick = {

                    val encryptedPassword = encryptMD5(userViewModel.password)

                    val checkLogin = db.getUserByUsernameAndPassword(userViewModel.email,encryptedPassword)
                    Log.i("DB_LOGIN", "loginScreen: $checkLogin")

                    if(userViewModel.email == ""){
                        Util.toastToText(context,"Masukan User Anda")
                    }else if (userViewModel.password == ""){
                        Util.toastToText(context, "Masukan Password Anda")
                    }else if(checkLogin) {

                        editor.putBoolean(Util.RememberME,userViewModel.rememberMe) // di benerin getFilm nya baru sesuaikan lagi false nya
                        editor.putString(Util.idlUser,db.getUserIdByEmail(userViewModel.email))
                        editor.putString(Util.emailUser,userViewModel.email)
                        editor.putString(Util.nameUser,db.getUsernameByEmail(userViewModel.email))
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
                Modifier.padding(16.dp),
                colors = mainActivity.defaultButtonColor()
            ){
                Text("Login",color = TextWhite)
            }
            Text("Buat akun baru mu sekarang!",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(ScreenRoute.SignupScreen.route)
                },
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
        }
    }
}