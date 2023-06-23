package com.example.havefunapp.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.havefunapp.R
import com.example.havefunapp.model.Movies
import com.example.havefunapp.ui.theme.OrangeYellow1
import com.example.havefunapp.ui.theme.OrangeYellow2
import com.example.havefunapp.ui.theme.OrangeYellow3
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.util.Util
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun MobileMovieScreen(
    popularList: MutableList<Movies>,
    topRatedData: MutableList<Movies>,
    upComingData: MutableList<Movies>,
    nowPlayingData: MutableList<Movies>
) {

    val gradientColors = listOf(
        OrangeYellow1,
        OrangeYellow2,
        OrangeYellow3
    )
    val primaryColor = Brush.linearGradient(gradientColors)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black).border(2.dp, OrangeYellow2)
    ){
        Column {
            TopAppBar(
                modifier = Modifier.border(2.dp,OrangeYellow2),
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dunia Film",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            color = OrangeYellow2, // Misalnya, gunakan warna OrangeYellow2
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButtonSearch(primaryColor,buttonSize = 35.dp, imageVector = Icons.Default.Search){

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButtonSearch(primaryColor,buttonSize = 35.dp, imageVector = Icons.Default.Person){

                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Back",
                            tint = OrangeYellow2
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
            )
            LazyColumn {
                item {  TextCenter("Now Playing") }
                item {
                    val state = rememberPagerState()

                    HorizontalPager(
                        modifier = Modifier.padding(16.dp),
                        count = nowPlayingData.size,
                        state = state
                    ) { page ->
                        val imageLoader = ImageLoader(context)

                        val painter = rememberImagePainter(
                            data = "https://image.tmdb.org/t/p/original/${nowPlayingData[page].backDrop}",
                            imageLoader = imageLoader
                        )
                        Column(
                            modifier = Modifier.size(300.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextHeaderImageSlide(nowPlayingData[page].title)
                            Box(
                                modifier = Modifier.border(0.5.dp, OrangeYellow2)
                            ){
                                Image(
                                    painter = painter,
                                    contentDescription = ""
                                )

                                if (painter.state is ImagePainter.State.Loading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color.White
                                    )
                                }
                            }
                            DotsIndicator(
                                totalDots = nowPlayingData.size,
                                selectedIndex = state.currentPage,
                                selectedColor = OrangeYellow2,
                                unSelectedColor = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(4.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextLeft("Popular Movie")
                        TextRight("Lihat lebih banyak")
                    }
                    HorizontalPagerImageUrl(imageItems = popularList,context)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextLeft("Top Rated")
                        TextRight("Lihat lebih banyak")
                    }
                    HorizontalPagerImageUrl(imageItems = topRatedData,context)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextLeft("Up Coming")
                        TextRight("Lihat lebih banyak")
                    }
                    HorizontalPagerImageUrl(imageItems = upComingData,context)
                    Spacer(modifier = Modifier.height(32.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                    ){
                        Column(
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Dunia Film", textAlign = TextAlign.Center)
                            Text("Dunia Film", textAlign = TextAlign.Center)
                            Text("Dunia Film", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TextLeft(s: String) {
    Text(
        text = s,
        color = TextWhite,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 17.sp,
            shadow = Shadow(
                color = OrangeYellow2,
                offset = Offset(1f,1f),
                blurRadius = 2f
            )
        )
    )
}

@Composable
fun TextCenter(s: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = s,
        color = TextWhite,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 24.sp,
            shadow = Shadow(
                color = OrangeYellow2,
                offset = Offset(1f,1f),
                blurRadius = 2f
            )
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TextHeaderImageSlide(s: String) {
    Text(
        text = s,
        color = OrangeYellow2,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 17.sp,
            shadow = Shadow(
                color = OrangeYellow2,
                offset = Offset(1f,1f),
                blurRadius = 2f
            )
        )
    )
}


@Composable
fun TextRight(s: String) {
    Text(
        text = s,
        color = OrangeYellow2,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 12.sp,
            shadow = Shadow(
                color = Color.White,
                offset = Offset(1f,1f),
                blurRadius = 2f
            ),
            textDecoration = TextDecoration.Underline
        )
    )
}

@Composable
fun IconButtonSearch( backgroundColor : Brush,buttonSize: Dp,imageVector: ImageVector,onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .background(backgroundColor, shape = CircleShape)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Search",
            tint = Color.Black
        )
    }
}



@Composable
fun HorizontalPagerImageUrl(imageItems: MutableList<Movies>,context: Context) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        items(imageItems.size){
            ImageCardUrl(imageItems[it].title,imageItems[it].posterPath,context){

            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCardUrl(
    title: String,
    imageRes: String,
    context: Context,
    onClick: () -> Unit
) {
    val imageLoader = ImageLoader(context)

    val painter = rememberImagePainter(
        data = "https://image.tmdb.org/t/p/original/$imageRes",
        imageLoader = imageLoader
    )

    Column(modifier = Modifier
        .padding(3.dp)
        .size(225.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
        ){
        TextHeaderImageSlide(
            title
        )
        Box(
            modifier = Modifier.border(0.5.dp, OrangeYellow2).clickable {
                onClick
            }
        ){
            Image(
                painter = painter,
                contentDescription = null,
            )
            if (painter.state is ImagePainter.State.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}











