package com.example.havefunapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.havefunapp.R
import com.example.havefunapp.ui.theme.TextWhite

data class Message(
    val sender: String,
    val content: String,
    val date: String
)

@Composable
fun ChatScreen() {

    val chatMessages = remember {
        mutableStateListOf(
            Message(sender = "User1", content = "Hello", date = "2023-06-22 09:00"),
            Message(sender = "User2", content = "Hi there!", date = "2023-06-22 09:05"),
            Message(sender = "User1", content = "How are you?", date = "2023-06-22 09:10")
        )
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        ChatTopBar(painterResource(R.drawable.aldian_rahman),"Aldian Rahman")
        Box(modifier = Modifier.weight(1f)) {
            Column {
                Conversation(messages = chatMessages)
            }
        }
        MessageInput()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    profilePhoto: Painter,
    profileName: String
) {
    TopAppBar(
        { Text(
            text = profileName,
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )},
        modifier = Modifier,
        {IconButton(onClick = {

        }) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }},{
            Image(
            painter = profilePhoto,
            contentDescription = "Profile Photo",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
           }, TopAppBarDefaults.windowInsets, TopAppBarDefaults.smallTopAppBarColors(),null,
    )
}

@Composable
fun Conversation(messages: List<Message>) {
    var endPosition by remember { mutableStateOf(false) }
    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        item { Text("20 Juni 2023", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
        items(messages) { message ->
            endPosition = message.sender == "User2"
            endPosition = message.sender != "User1"
            MessageItem(message = message,endPosition)
        }
    }
}

@Composable
fun MessageItem(message: Message, endPosition: Boolean) {
    // Tampilan item pesan dalam daftar
    // Contoh: Menampilkan pengirim dan isi pesan
    val textAlign = if (endPosition){
        TextAlign.End
    }else {
        TextAlign.Start
    }
    Text(
        text = message.content,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .background(if (endPosition) Color.White else Color.Transparent),
        textAlign = textAlign
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput() {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text(text = "Type a message") },
        )
        Button(
            onClick = { /* Logic untuk mengirim pesan */ },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(text = "Send")
        }
    }
}