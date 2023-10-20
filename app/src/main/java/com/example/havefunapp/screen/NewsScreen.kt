package com.example.havefunapp.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.havefunapp.model.ButtonTop
import com.example.havefunapp.model.HeadlineData
import com.example.havefunapp.ui.theme.OrangeYellow3

@Composable
fun NewsScreen(
    items: List<ButtonTop>,
    headlineData: List<HeadlineData>,
){
    val context = LocalContext.current
    var selectedButtonIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 60.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    "Berita",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangeYellow3,
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            LazyRow(
                modifier = Modifier
//                .align(Alignment.TopCenter)
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(items.size){
                    val isClicked = selectedButtonIndex == it
                    Button(
                        onClick = {
                            Toast.makeText(context, ""+items[it].title, Toast.LENGTH_SHORT).show()
                            selectedButtonIndex = it
                        },
                        modifier = Modifier
                            .padding(8.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = ButtonDefaults
                            .buttonColors(
                                containerColor = if (isClicked) Color.LightGray else Color.Transparent
                            )
                    ){
                        Text(items[it].title)
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(headlineData.size){
                    when(selectedButtonIndex){
                        0 -> HeadlineNewsItem(headlineData,it)

                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.5f)
            ){
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(0.5f)
            ){
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun HeadlineNewsItem(newsData: List<HeadlineData>, it: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Text(
            newsData[it].titleNews,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(5.dp)
        )
        Text(
            newsData[it].detailNews,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}
