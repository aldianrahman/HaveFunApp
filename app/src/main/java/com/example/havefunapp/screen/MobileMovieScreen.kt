package com.example.havefunapp.screen

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.havefunapp.MainActivity
import com.example.havefunapp.model.Movies
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.OrangeYellow1
import com.example.havefunapp.ui.theme.OrangeYellow2
import com.example.havefunapp.ui.theme.OrangeYellow3
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun MobileMovieScreen(
    popularData: MutableList<Movies>,
    topRatedData: MutableList<Movies>,
    upComingData: MutableList<Movies>,
    nowPlayingData: MutableList<Movies>,
    editor: SharedPreferences.Editor?,
    navController: NavHostController?,
    exitToApp:(Boolean)->Unit
) {
    var refreshDataPopular by remember { mutableStateOf(false) }
    var dialogDetail by remember { mutableStateOf(false) }
    var refreshDataTopRated by remember { mutableStateOf(false) }
    var refreshDataUpComing by remember { mutableStateOf(false) }
    var pagePopular by remember { mutableStateOf(2) }
    var pageTopRated by remember { mutableStateOf(2) }
    var pageUpComing by remember { mutableStateOf(2) }
    var idDetail by remember { mutableStateOf(0) }
    val gradientColors = listOf(
        OrangeYellow1,
        OrangeYellow2,
        OrangeYellow3
    )
    val primaryColor = Brush.linearGradient(gradientColors)
    val context = LocalContext.current
    val mainActivity = MainActivity()

    mainActivity.BackPressHandler {
        exitToApp(it)
    }

    val mainTransport = MainTransport()

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
                            editor?.clear()
                            editor?.apply()
                            navController?.navigate(ScreenRoute.SplashScreen.route)
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
                    HorizontalPagerImageUrl(imageItems = popularData,context,true, sendDataId = {id->
                       idDetail = id
                    }, onClick = { bool->
                        dialogDetail = bool
                    }){
                        mainActivity.getDataApi(Util.popular,pagePopular++,null,mainTransport,context,"Get_Popular_On_Last_Item $pagePopular",popularData,
                            onSuccess = {bool->
                                refreshDataPopular = bool
                            }
                        ){data->

                    }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextLeft("Top Rated")
                        TextRight("Lihat lebih banyak")
                    }
                    HorizontalPagerImageUrl(imageItems = topRatedData,context,true, sendDataId = {id->
                        idDetail = id
                    }, onClick = {bool->
                        dialogDetail = bool
                    }){
                        mainActivity.getDataApi(Util.popular,pageTopRated++,null,mainTransport,context,"Get_Top_Rated_On_Last_Item $pageTopRated",topRatedData,
                            onSuccess = {bool->
                                refreshDataTopRated = bool
                            }
                        ){data->

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        TextLeft("Up Coming")
                        TextRight("Lihat lebih banyak")
                    }
                    HorizontalPagerImageUrl(imageItems = upComingData,context,true, sendDataId = {id->
                        idDetail = id
                    }, onClick = {bool->
                        dialogDetail = bool
                    }){
                        mainActivity.getDataApi(Util.popular,pageUpComing++,null,mainTransport,context,"Get_Up_Coming_On_Last_Item $pageUpComing",upComingData,
                            onSuccess = {bool->
                                refreshDataUpComing = bool
                            }
                        ){data->

                        }
                    }
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
        if (refreshDataPopular){
            HorizontalPagerImageUrl(popularData,context,false, sendDataId = {

            }, onClick = {bool->

            }){

            }
        }
        if (refreshDataTopRated){
            HorizontalPagerImageUrl(topRatedData,context,false, sendDataId = {

            }, onClick = {

            }){

            }
        }
        if (refreshDataUpComing){
            HorizontalPagerImageUrl(upComingData,context,false, sendDataId = {

            },onClick = {

            }){

            }
        }
        if (dialogDetail) {
            AlertDialog(
                modifier = Modifier.fillMaxSize(),
                onDismissRequest = { dialogDetail = false },
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = idDetail.toString(),
                        color = TextWhite,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 30.sp,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f,2f),
                                blurRadius = 4f
                            )
                        )
                    )
                },
                text = {
                    Text("Isi")
                },
                confirmButton = {
                    val mainActivity  = MainActivity()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Button(
                            colors = mainActivity.defaultButtonColor(),
                            onClick = { Util.toastToText(context,"Data Tersimpan") },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ThumbUp,
                                contentDescription = "ThumbUp",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }

                        Button(
                            colors = mainActivity.defaultButtonColor(),
                            onClick = { dialogDetail = false }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close Icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Red
                            )
                        }
                    }
                }
            )
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
        ),
        textAlign = TextAlign.Center
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
fun HorizontalPagerImageUrl(
    imageItems: MutableList<Movies>,
    context: Context,
    isVisible:Boolean,
    sendDataId:(Int)-> Unit,
    onClick: (Boolean) -> Unit,
    onLastItemVisible:()->Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            .alpha(if (isVisible) 1f else 0f)
    ) {
        items(imageItems.size){index->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageCardUrl(imageItems[index].title,imageItems[index].posterPath,context){bool->
                    onClick(bool)
                }
                Text(imageItems[index].score, color = OrangeYellow2)
            }
            if (index == imageItems.size - 1) {
                onLastItemVisible()
            }
            sendDataId(imageItems[index].id)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCardUrl(
    title: String,
    imageRes: String,
    context: Context,
    onClick: (Boolean) -> Unit
) {
    val imageLoader = ImageLoader(context)

    val painter = rememberImagePainter(
        data = "https://image.tmdb.org/t/p/original/$imageRes",
        imageLoader = imageLoader
    )

    Column(modifier = Modifier
        .padding(3.dp)
        .size(225.dp)
        .clickable {
        onClick(true)
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
        ){
        TextHeaderImageSlide(
            title
        )
        Box(
            modifier = Modifier.border(0.5.dp, OrangeYellow2)
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











