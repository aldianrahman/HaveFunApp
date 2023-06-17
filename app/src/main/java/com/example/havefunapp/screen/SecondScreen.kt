package com.example.havefunapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.TextWhite


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(index: String?) {
    Box(modifier = Modifier
        .background(DeepBlue)
        .fillMaxSize()){
        if (index != null) {

            var text by remember {
                mutableStateOf("")
            }

            val onChange: (String) -> Unit = {}

            var isNameChange by remember {
                mutableStateOf(false)
            }

            val keyboardType: KeyboardType = KeyboardType.Text


            val medium: Dp = 16.dp



//            BottomMenu(
//                editor = null,
//                items = listOf(
//                    BottomMenuContent("Home", R.drawable.ic_home),
//                    BottomMenuContent("Meditate", R.drawable.ic_bubble),
//                    BottomMenuContent("Sleep", R.drawable.ic_moon),
//                    BottomMenuContent("Music", R.drawable.ic_music),
//                    BottomMenuContent("Profile", R.drawable.ic_profile),
//                ), modifier = Modifier.align(Alignment.BottomCenter)
//                , navController = null,
//                initialSelectedItemIndex = index.toInt()
//            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoolTextField(
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors,
    value: String,
    placeholder: String,
    modifier: Modifier,
    onValueChange: (String) -> Unit = {},
) {
    var isNameChange by remember {
        mutableStateOf(false)
    }
    var isFocusChange by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("")
    }
    text = value
    val medium: Dp = 16.dp

    OutlinedTextField(

//        value = username,
//        onValueChange = { newUsername -> username = newUsername },
//        placeholder = { Text("Enter your Name") },
//        modifier = paddingUp,
//        colors = mainActivity.defaultTextFieldColor()

        modifier = modifier,
        colors = colors,
        isError = isError,
        trailingIcon = trailingIcon,
//            .onFocusChanged {
//                if (isNameChange) {
////                    isFocusChange = true
////                    onFocusChange(isFocusChange)
//                }
//            },
        label = { Text(text = placeholder, color = TextWhite) },
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
            isNameChange = true
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}
