package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.transport.IonMaster
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.ui.theme.primaryColor
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util.Companion.toastToText


@SuppressLint("ComposableNaming", "PrivateResource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signupScreen(context: Context, navController: NavHostController, db: UserDao) {

    var username by remember { mutableStateOf("") }
    var strength by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf(true) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val mainTransport = MainTransport()
    val mainActivity = MainActivity()
    val onBack = { toastToText(context,"Tekan tombol 'Back' sekali lagi untuk menutup aplikasi") }

    mainActivity.BackPressHandler(onBackPressed = onBack)

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
                placeholder = { Text("Enter your Name") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                colors = mainActivity.defaultTextFieldColor()
            )
            TextField(
                value = email,
                onValueChange = { newEmail -> email = newEmail
                    showError = !isEmailValid(newEmail)
                                },
                isError = showError,
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                colors = mainActivity.defaultTextFieldColor(),
                trailingIcon = {
                    if (showError) {
                        iconTextField(Color.Red)
                    }else if(db.getUserByEmail(email)){
                        iconTextField(Color.Red)
                    }
                    else{
                        iconTextField(Color.Green)
                    }
                }

            )
            TextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    val passwordStrength = checkPasswordStrength(newPassword)
                    // Lakukan tindakan berdasarkan kekuatan password
                    // Misalnya, tampilkan pesan kekuatan password di bawah TextField
                    when (passwordStrength) {
                        PasswordStrength.WEAK -> {
                            strength = "w"
                        }
                        PasswordStrength.MEDIUM -> {
                            strength = "m"
                        }
                        PasswordStrength.STRONG -> {
                            strength = "s"
                        }
                    }
                },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(com.google.android.material.R.drawable.design_ic_visibility_off) else painterResource(
                                com.google.android.material.R.drawable.design_ic_visibility
                            ),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                colors = mainActivity.defaultTextFieldColor()
            )
            when (strength) {
                "w" -> {
                    Text("Password Weak", color = Color.Red)
                }
                "m" -> {
                    Text("Password Medium",color = Color.Yellow)
                }
                "s" -> {
                    Text("Password Strong",color = Color.Green)
                }
            }

            TextField(
                value = confirmPassword,
                onValueChange = { newPassword -> confirmPassword = newPassword },
                placeholder = { Text("Renter your password") },
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = if (passwordVisibility) painterResource(com.google.android.material.R.drawable.design_ic_visibility_off) else painterResource(
                                com.google.android.material.R.drawable.design_ic_visibility
                            ),
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                },
                colors = mainActivity.defaultTextFieldColor()
            )
            Button(
                enabled = isButtonEnabled,
                onClick = {
                    if (username.isEmpty()) {
                        toastToText(context, "Please enter your username")
                    }else if (email.isEmpty()) {
                        toastToText(context, "Please enter your email")
                    }
                    else if (password.isEmpty()) {
                        toastToText(context, "Please enter your password")
                    } else if (confirmPassword.isEmpty()) {
                        toastToText(context, "Please confirm your password")
                    } else if (password != confirmPassword) {
                        toastToText(context, "Passwords do not match")
                    } else if(db.getUserByEmail(email)){
                        showError = true
                        toastToText(context, "Email already register")
                    }
                    else {
                        if(strength != "w"){
                            isButtonEnabled = false
                            mainTransport.updateUserSignUp(
                                username,
                                password,
                                email,
                                context,
                                object : IonMaster.IonCallback {
                                    override fun onReadyCallback(
                                        errorMessage: String?,
                                        `object`: Any?
                                    ) {
                                        if (`object` != null) {
                                            toastToText(context, "Signup successful")
                                            navController.navigate(ScreenRoute.LoginScreen.route)
                                        } else {
                                            isButtonEnabled = true
                                            toastToText(context, "Signup failed")
                                        }
                                    }

                                })

                        }else{
                            toastToText(context,"Password anda terlalu lemah")
                        }


                    }


                },
                colors = mainActivity.defaultButtonColor()
            ) {
                Text("Register",color = TextWhite)
            }
            Text("Sudah memiliki akun",
                modifier = Modifier.clickable {
                    navController.navigate(ScreenRoute.LoginScreen.route)
                }
            )
        }
    }
}

fun checkPasswordStrength(password: String): PasswordStrength {
    var strength = PasswordStrength.WEAK

    if (password.length >= 8) {
        strength = PasswordStrength.MEDIUM

        val containsUpperCase = password.any { it.isUpperCase() }
        val containsLowerCase = password.any { it.isLowerCase() }
        val containsDigit = password.any { it.isDigit() }
        val containsSpecialChar = password.any { it.isSpecialChar() }

        if (containsUpperCase && containsLowerCase && containsDigit && containsSpecialChar) {
            strength = PasswordStrength.STRONG
        }
    }

    return strength
}

fun Char.isSpecialChar(): Boolean {
    return this.isLetterOrDigit().not()
}

enum class PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}




@Composable
fun iconTextField(
    color:Color
) {
    Icon(
        painter = painterResource(R.drawable.eye_closed_icon),
        contentDescription = "Error Icon",
        tint = color
    )
}





fun isEmailValid(email: String): Boolean {
    val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailPattern.toRegex())
}
