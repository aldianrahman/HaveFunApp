package com.example.havefunapp.viewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.example.havefunapp.dao.UserDao
import com.example.havefunapp.db.AppDatabase
import com.example.havefunapp.screen.iconOutlinedTextField
import com.google.android.material.R

class UserViewModel : ViewModel() {
    // Mutable state untuk menyimpan username dan password
//    @SuppressLint("StaticFieldLeak")
//    val context = LocalContext.current
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rePassword by mutableStateOf("")
    var passwordVisibility by mutableStateOf(false)
    var showError by mutableStateOf(true)
    var rememberMe by mutableStateOf(false)
//    val db: UserDao = AppDatabase.getInstance(context)?.userDao()!!

    // Fungsi untuk melakukan proses login
    fun performLogin(): Boolean {
        // Di sini Anda bisa menambahkan logika untuk memeriksa kredensial pengguna,
        // misalnya membandingkan username dan password dengan data yang valid dari server atau database.
        // Untuk keperluan contoh, kami hanya akan mengecek apakah username dan password bukan string kosong.
        return username.isBlank() && password.isBlank() && email.isBlank() && rePassword.isBlank()
    }

    fun successLogin(): Boolean{

        return username == "aldian" && password == "aldian" && rePassword == "aldian" && email == "aldian"
    }
//
//    fun successSignup(): Boolean{
//
//        return db.getUserByEmail(email)
//    }

    fun passwordVisibility(value: Boolean): VisualTransformation {
        val visualTransformation = if (value){
            VisualTransformation.None
        }else{
            PasswordVisualTransformation()
        }
        return visualTransformation
    }

    @Composable
    @SuppressLint("PrivateResource")
    fun passwordVisibilityIcon(value: Boolean): Painter {
        return if (value) painterResource(R.drawable.design_ic_visibility_off) else painterResource(
            R.drawable.design_ic_visibility
        )
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailPattern.toRegex())
    }

    fun fieldEmpty(): String {

        val emptyField = if (username.isBlank()){
         "Your Name"
        } else if(email.isBlank()) {
            "Your Email"
        }else if (password.isBlank()){
            "Your Password"
        }else if (rePassword.isBlank()){
            "Your Reenter Password"
        } else {
            "OkeNext"
        }

        return emptyField
    }


}