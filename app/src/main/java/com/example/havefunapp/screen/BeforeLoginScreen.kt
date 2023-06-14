package com.example.havefunapp.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.havefunapp.R
import com.example.havefunapp.transport.IonMaster
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.primaryColor
import com.example.havefunapp.util.ScreenRoute


fun toastToText(
    context:Context,
    text: String
) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(

){
    val context = LocalContext.current
    var user by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
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
            Text("Please Login")
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            TextField(
                value = user,
                onValueChange = { newUser -> user = newUser },
                placeholder = { Text("Enter your Username") },
                modifier = Modifier.fillMaxWidth().padding(15.dp)
            )
            TextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(R.drawable.eye_closed_icon) else painterResource(R.drawable.eye_open_icon),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )
            Button(
                onClick = {
                    if(user == ""){
                        toastToText(context,"Masukan User Anda")
                    }else if (password == ""){
                        toastToText(context, "Masukan Password Anda")
                    }else{
                        toastToText(context,"Login Berhasil")
                    }

                }
            ){
                Text("Login")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signupScreen(navController: NavHostController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var mainTransport = MainTransport()

    Box(
        modifier = Modifier
            .background(primaryColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Please Signup")
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
            TextField(
                value = username,
                onValueChange = { newUsername -> username = newUsername },
                placeholder = { Text("Enter your Username") },
                modifier = Modifier.fillMaxWidth().padding(15.dp)
            )
            TextField(
                value = password,
                onValueChange = { newPassword -> password = newPassword },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            TextField(
                value = confirmPassword,
                onValueChange = { newConfirmPassword -> confirmPassword = newConfirmPassword },
                placeholder = { Text("Confirm your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Button(
                onClick = {
                    if (username.isEmpty()) {
                        toastToText(context, "Please enter your username")
                    } else if (password.isEmpty()) {
                        toastToText(context, "Please enter your password")
                    } else if (confirmPassword.isEmpty()) {
                        toastToText(context, "Please confirm your password")
                    } else if (password != confirmPassword) {
                        toastToText(context, "Passwords do not match")
                    } else {
                        // Perform signup logic here
                        toastToText(context, "Signup successful")
                        navController.navigate(ScreenRoute.LoginScreen.route)
                        mainTransport.updateUserSignUp(username,password,
                            context,object : IonMaster.IonCallback {
                                override fun onReadyCallback(errorMessage: String?, `object`: Any?) {
                                    if (errorMessage!=null){
                                        toastToText(context,"Berhasil")
                                    }else{
                                        toastToText(context,"Gagal")
                                    }
                                }

                            })
                    }



                }
            ) {
                Text("Signup")
            }
        }
    }
}
