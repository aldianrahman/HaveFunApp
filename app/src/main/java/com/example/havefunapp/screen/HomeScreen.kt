@file:Suppress("UNUSED_EXPRESSION")

package com.example.havefunapp.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences.Editor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.model.ColorModelLight
import com.example.havefunapp.model.ColorModelDark
import com.example.havefunapp.model.ColorModelMedium
import com.example.havefunapp.model.Movies
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.AquaBlue
import com.example.havefunapp.ui.theme.Blue1
import com.example.havefunapp.ui.theme.Blue2
import com.example.havefunapp.ui.theme.Blue3
import com.example.havefunapp.ui.theme.BlueOcean
import com.example.havefunapp.ui.theme.ButtonBlue
import com.example.havefunapp.ui.theme.DarkerButtonBlue
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.util.BottomMenuContent
import com.example.havefunapp.util.Feature
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util
import com.example.havefunapp.util.standardQuadFromTo
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    context: Context,
    editor: Editor,
    salam: String,
    hope: String,
    date: String,
    data: String,
    email: String,
    stringButton: List<String>,
    dataDefault: MutableList<Movies>,
    topRatedData: MutableList<Movies>,
    navController: NavHostController,
    upComingData: MutableList<Movies>
){
    var searchTexts by remember { mutableStateOf("") }
    val mainActivity = MainActivity()
    val mainTransport = MainTransport()
    var popular : MutableList<Movies> = dataDefault
    var topRated : MutableList<Movies> = mutableListOf()
    var upComing : MutableList<Movies> = mutableListOf()
    var loadDataSearch by remember { mutableStateOf(false) }




//    if (searchTexts != ""&&searchTexts.length > 3 && loadDataSearch) run {
//        stringFeature = dataDefault.subList(10,20)
//    } else{
//        stringFeature = dataDefault
//    }



    val onBack = {
        Util.toastToText(
            context,
            "Tekan tombol 'Back' sekali lagi untuk menutup aplikasi"
        )
    }

    val featureSection: MutableList<Feature> = mutableListOf()
    val featureSections: MutableList<Feature> = mutableListOf()

    var lastItemVisible by remember { mutableStateOf(false) }
    var loadingGetAPI by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(2) }


    val colorListDark = listOf(
        ColorModelDark("OrangeYellow3", 0xfff0bd28),
        ColorModelDark("Beige3", 0xfff9a27b),
        ColorModelDark("LightGreen3", 0xff11d79b),
        ColorModelDark("BlueViolet3", 0xff8f98fd),
        ColorModelDark("Red3", 0xffd32f2f),
        ColorModelDark("Blue3", 0xff1976d2),
        ColorModelDark("Green3", 0xff388e3c)
    )

    val colorListLight = listOf(
        ColorModelLight("OrangeYellow1", 0xfff4cf65),
        ColorModelLight("Beige1", 0xfffdbda1),
        ColorModelLight("LightGreen1", 0xff54e1b6),
        ColorModelLight("BlueViolet1", 0xffaeb4fd),
        ColorModelLight("Red1", 0xffe57373),
        ColorModelLight("Blue1", 0xff64b5f6),
        ColorModelLight("Green1", 0xff81c784),
    )

    val colorListMedium = listOf(
        ColorModelMedium("OrangeYellow2", 0xfff1c746),
        ColorModelMedium("Beige2", 0xfffcaf90),
        ColorModelMedium("LightGreen2", 0xff36ddab),
        ColorModelMedium("BlueViolet2", 0xff9fa5fe),
        ColorModelMedium("Red2", 0xfff44336),
        ColorModelMedium("Blue2", 0xff2196f3),
        ColorModelMedium("Green2", 0xff4caf50),
        )

    var onChipIndex by remember {
        mutableStateOf(0)
    }
    var loadData by remember {
        mutableStateOf(true)
    }

    if (onChipIndex == 0){
        popular = dataDefault
        if (lastItemVisible){
            setData(popular,colorListLight,colorListMedium,colorListDark,featureSection)
        }else{
            setData(popular,colorListLight,colorListMedium,colorListDark,featureSection)
        }
    }else if (onChipIndex == 1) {
        topRated = topRatedData
        if (lastItemVisible){
            setData(topRated,colorListLight,colorListMedium,colorListDark,featureSection)
        }else{
            setData(topRated,colorListLight,colorListMedium,colorListDark,featureSection)
        }
    }else if (onChipIndex == 2) {
        upComing = upComingData
        if (lastItemVisible){
            setData(upComing,colorListLight,colorListMedium,colorListDark,featureSection)
        }else{
            setData(upComing,colorListLight,colorListMedium,colorListDark,featureSection)
        }
    }





//    mainActivity.BackPressHandler(onBackPressed = onBack)
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ){
        Column {
            GreetingSection(salam,hope,date){searchText->
                searchTexts = searchText
            }
            ChipSection(chips = stringButton){selectIndex ->
                onChipIndex = selectIndex
            }
            CurrentMeditation(data,email,editor,navController)
//            checkLogin(email,navController)
            FeatureSection(
                onChipIndex,
                stringButton,
                features = featureSection
            ) {
                lastItemVisible = true
            }
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent("Home", R.drawable.ic_home),
                BottomMenuContent("Meditate", R.drawable.ic_bubble),
                BottomMenuContent("Sleep", R.drawable.ic_moon),
                BottomMenuContent("Music", R.drawable.ic_music),
                BottomMenuContent("Logout", R.drawable.ic_profile),
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
            , navController = navController
        )
        if (lastItemVisible){

            lastItemVisible = false

            if(onChipIndex ==0){
                mainActivity.getDataApi(Util.popular,page++,null,mainTransport,context,"Halaman 2 ",popular,
                    onSuccess = {bool->
                        loadingGetAPI = bool
                    }){

                }
            }else if(onChipIndex ==1){
                mainActivity.getDataApi(Util.topRated,page++,null,mainTransport,context,"Halaman 2 ",topRated,
                    onSuccess = {bool->
                        loadingGetAPI = bool
                    }){

                }
            }else if(onChipIndex ==2){
                mainActivity.getDataApi(Util.topRated,page++,null,mainTransport,context,"Halaman 2 ",upComing,
                    onSuccess = {bool->
                        loadingGetAPI = bool
                    }){

                }
            }



        }
        if (loadingGetAPI){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { /* Tidak melakukan apa-apa saat di klik */ },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = TextWhite
                    )
                    Text("Sedang memuat data...")
                }
                kotlinx.coroutines.GlobalScope.launch {
                    kotlinx.coroutines.delay(2000)
                    loadingGetAPI = false
                    // Langkah 4: Kode setelah delay
                    // Kode yang ingin dieksekusi setelah delay dapat ditempatkan di sini
                }
            }
        }
    }
}

@Composable
fun checkLogin(email: String, navController: NavHostController) {
    if (email.isNullOrBlank() && email.isEmpty()){
        navController.navigate(ScreenRoute.LoginScreen.route)
    }
}

fun setData(
    stringFeature: MutableList<Movies>,
    colorListLight: List<ColorModelLight>,
    colorListMedium: List<ColorModelMedium>,
    colorListDark: List<ColorModelDark>,
    featureSection: MutableList<Feature>
) {
    for (i in stringFeature.indices) {

        val lightColorIndex = i % colorListLight.size
        val mediumColorIndex = i % colorListMedium.size
        val darkColorIndex = i % colorListDark.size

        val feature = Feature(
            title = stringFeature[i].title,
            score = stringFeature[i].score,
            release_date = stringFeature[i].release_date,
            overview = stringFeature[i].overview,
            backDrop = stringFeature[i].backDrop,
            posterPath = stringFeature[i].posterPath,
            iconId = R.drawable.ic_videocam,
            lightColor = Color(colorListLight[lightColorIndex].value),
            mediumColor = Color(colorListMedium[mediumColorIndex].value),
            darkColor = Color(colorListDark[darkColorIndex].value)
        )
        featureSection.add(feature)
    }
}


@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0,
    navController: NavHostController?
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(5.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
//                selectedItemIndex = index
//                if (selectedItemIndex == 0){
//                    navController?.navigate(ScreenRoute.HomeScreen.route)
//                }else{
//                    Util.toastToText(context, "Clicked : $selectedItemIndex")
//                }
//                else if (selectedItemIndex == 1){
//                    navController?.navigate(ScreenRoute.SecondScreen.withArgs(selectedItemIndex.toString()))
//                }
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconID),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if(isSelected) activeTextColor else inactiveTextColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingSection(
    greeting: String,
    wish: String,
    date: String,
    context : Context = LocalContext.current,
    onSearchAPI:(String) -> Unit
){
    var searchClick by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ){
        if (searchClick){
            TextField(
                value = searchText,
                onValueChange = {newSearch->
                    searchText = newSearch
                },
                placeholder = { Text(text = "Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(color = Color.Black),
                trailingIcon = {
                    Row {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Close Search",
                            tint = Color.Blue,
                            modifier = Modifier.padding(end = 8.dp)
                                .clickable {
                                if (searchText.length > 3){
                                    onSearchAPI(searchText)
                                }else{
                                    Util.toastToText(context,"Minimal 3 Karakter")
                                }
                            }
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Search",
                            tint = Color.Red,
                            modifier = Modifier.padding(end = 8.dp)
                                .clickable {
                                    searchText =  ""
                                    onSearchAPI("")
                                searchClick = false
                            }
                        )
                    }
                }
            )
        }else{
            Column(
                verticalArrangement = Arrangement.Center,

                ) {
                Text(
                    date,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextWhite
                )
                Text(
                    greeting,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    wish,
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .clickable { searchClick = true }
        )


    }
}



@Composable
fun ChipSection(
    chips: List<String>,
    onChipSelected: (Int) -> Unit
){
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    LazyRow {
        items(chips.size){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
//                        Util.toastToText(context,"$selectedChipIndex : "+chips[selectedChipIndex])
                        onChipSelected(selectedChipIndex)
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) ButtonBlue
                        else DarkerButtonBlue
                    )
                    .padding(15.dp)
            ){
                Text(
                    text = chips[it],
                    color= TextWhite
                )
            }
        }
    }
}

@Composable
fun LogoutDialog(
    onConfirmLogout: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { onDismissRequest.invoke() },
            title = {
                Text(text = stringResource(R.string.logout_title))
            },
            text = {
                Text(text = stringResource(R.string.logout_message))
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmLogout.invoke()
                        showDialog.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onDismissRequest.invoke()
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun CurrentMeditation(
    data: String,
    email: String,
    editor: Editor?,
    navController: NavHostController?
){
    val contex = LocalContext.current

    var showUserBox by remember {
        mutableStateOf(false)
    }

    val gradientColors = listOf(

        Blue1,
        Blue2,
        Blue3,
        BlueOcean

    )

    val gradient = Brush.verticalGradient(gradientColors)
    var logout by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (showUserBox) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = "ThumbUp",
            modifier = Modifier.size(30.dp).clickable {
                showUserBox = !showUserBox
            },
            tint = Color.White
        )
        if (showUserBox){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(gradient)
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Unspecified,
                                    Color.Transparent
                                ),
                                startX = 0.1f
                            )
                        )
                ) {
                    Text(
                        text = email,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                    Text(
                        text = data,
                        color = TextWhite,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(1f,1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Play",
                    tint = Color.Red,
                    modifier = Modifier.background(Color.White)
                        .size(24.dp)
                        .clickable {
                            logout = true
                        }
                )

                if (logout) {
                    LogoutDialog(
                        onConfirmLogout = {
                            editor?.clear()
                            editor?.apply()
                            editor?.commit()
                            navController?.navigate(ScreenRoute.SplashScreen.route)
                        },
                        onDismissRequest = {
                            logout = false
                        }
                    )
                }
            }
        }
    }


    }

@Composable
fun FeatureSection(
    onChipIndex: Int,
    stringButton: List<String>,
    features: List<Feature>,
    onLastItemVisible: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            stringButton[onChipIndex],
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            itemsIndexed(features) { index, feature ->
                FratureItem(feature = feature)

                // Menampilkan pesan toast "Terakhir" jika indeks item adalah indeks terakhir
                if (index == features.size - 1) {
//                    Util.toastToText(context = context , "Terakhir")
                    onLastItemVisible()
                }
            }
        }
    }
}

@Composable
fun FratureItem(
    feature: Feature
){
    val contex = LocalContext.current
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ){
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        //Medium colored path
        val mediumColoredPoint1 = Offset(0f,height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f,height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f,height * 0.05f)
        val mediumColoredPoint4 = Offset(width *0.7f,height * 0.6f)
        val mediumColoredPoint5 = Offset(width * 1.4f,-height.toFloat())

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x,mediumColoredPoint2.y)
            standardQuadFromTo(mediumColoredPoint1,mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2,mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3,mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4,mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }

        var alertDialogOpen = remember { mutableStateOf(false) }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ){
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = feature.lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ){
            Column {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
                Text(
                    text = feature.release_date,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 12.sp,
                        shadow = Shadow(
                            color = TextWhite,
                            offset = Offset(1f,1f),
                            blurRadius = 2f
                        )
                    )
                )
            }
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = feature.score,
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {


                        alertDialogOpen.value = true
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )

            if (alertDialogOpen.value) {
                AlertDialog(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = feature.lightColor,
                    onDismissRequest = { alertDialogOpen.value = false },
                    title = {
                        Text(
                            textAlign = TextAlign.Center,
                            text = feature.title,
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
                           clickDetail(feature.overview,feature.backDrop,feature.posterPath)
                    },
                    confirmButton = {
                        val mainActivity  = MainActivity()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Button(
                                colors = mainActivity.defaultButtonColor(),
                                onClick = { Util.toastToText(contex,"Data Tersimpan") },
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
                                onClick = { alertDialogOpen.value = false }
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
}



@Composable
fun clickDetail(overview: String, backdrop: String, posterPath: String) {
    val itemsList = (1)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ImageViewByUrl("https://image.tmdb.org/t/p/original/$posterPath")
        }
        items(itemsList) {
            // Composable item content
            Text(
                text = overview,
                color = TextWhite,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(1f,1f),
                        blurRadius = 2f
                    )
                )
            )
        }
        item {
            ImageViewByUrl("https://image.tmdb.org/t/p/original/$backdrop")
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageViewByUrl(url: String) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    val painter = rememberImagePainter(
        data = url,
        imageLoader = imageLoader,
        builder = {
            crossfade(true)
//            placeholder(R.drawable.loading_placeholder)
//            error(R.drawable.error_placeholder)
        }
    )

    var isFullScreen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.size(300.dp).clickable {
        isFullScreen = true
    }) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        if (painter.state is ImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
    if (isFullScreen) {
        FullScreenImageDialog(
            imageUrl = url,
            onDismiss = { isFullScreen = false }
        )
    }
}

@Composable
fun FullScreenImageDialog(imageUrl: String, onDismiss: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
                    .clickable { onDismiss.invoke() }
            )
        }
    }
}
