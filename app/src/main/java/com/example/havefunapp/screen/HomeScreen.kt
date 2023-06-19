package com.example.havefunapp.screen

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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.havefunapp.MainActivity
import com.example.havefunapp.R
import com.example.havefunapp.model.Movies
import com.example.havefunapp.transport.MainTransport
import com.example.havefunapp.ui.theme.AquaBlue
import com.example.havefunapp.ui.theme.Blue1
import com.example.havefunapp.ui.theme.ButtonBlue
import com.example.havefunapp.ui.theme.DarkerButtonBlue
import com.example.havefunapp.ui.theme.DeepBlue
import com.example.havefunapp.ui.theme.Green1
import com.example.havefunapp.ui.theme.OrangeYellow1
import com.example.havefunapp.ui.theme.Red1
import com.example.havefunapp.ui.theme.TextWhite
import com.example.havefunapp.util.BottomMenuContent
import com.example.havefunapp.util.Feature
import com.example.havefunapp.util.ScreenRoute
import com.example.havefunapp.util.Util
import com.example.havefunapp.util.standardQuadFromTo
import kotlin.random.Random


@Composable
fun HomeScreen(
    context: Context,
    editor: Editor,
    salam: String,
    harapan: String,
    date: String,
    data: String,
    email: String,
    stringButton: List<String>,
    stringFeature: MutableList<Movies>,
    navController: NavHostController
){
    val onBack = {
        Util.toastToText(
            context,
            "Tekan tombol 'Back' sekali lagi untuk menutup aplikasi"
        )
    }
    val mainActivity = MainActivity()
    val mainTransport = MainTransport()
    val featureSection: MutableList<Feature> = mutableListOf()

    var lastItemVisible by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf(2) }

    if (lastItemVisible){
        for (i in stringFeature.indices) {
            val colorIndex = Random.nextInt(360) // Rentang hue dari 0 hingga 359
            val lightnessVariation = Random.nextFloat() * 0.5f + 0.25f // Rentang kecerahan dari 0.25 hingga 0.75

            val lightColor = adjustLightness(colorIndex, lightnessVariation, 1.0f) // Kecerahan maksimal
            val mediumColor = adjustLightness(colorIndex, lightnessVariation, 0.75f) // Kecerahan sedang
            val darkColor = adjustLightness(colorIndex, lightnessVariation, 0.5f) // Kecerahan minimal

            val feature = Feature(
                title = stringFeature[i].title,
                score = stringFeature[i].score,
                release_date = stringFeature[i].release_date,
                overview = stringFeature[i].overview,
                backDrop = stringFeature[i].backDrop,
                posterPath = stringFeature[i].posterPath,
                iconId = R.drawable.ic_videocam,
                lightColor = Color(lightColor),
                mediumColor = Color(mediumColor),
                darkColor = Color(darkColor)
            )
            featureSection.add(feature)
        }
    }else{
        for (i in stringFeature.indices) {
            val colorIndex = Random.nextInt(360) // Rentang hue dari 0 hingga 359
            val lightnessVariation = Random.nextFloat() * 0.5f + 0.25f // Rentang kecerahan dari 0.25 hingga 0.75

            val lightColor = adjustLightness(colorIndex, lightnessVariation, 1.0f) // Kecerahan maksimal
            val mediumColor = adjustLightness(colorIndex, lightnessVariation, 0.75f) // Kecerahan sedang
            val darkColor = adjustLightness(colorIndex, lightnessVariation, 0.5f) // Kecerahan minimal

            val feature = Feature(
                title = stringFeature[i].title,
                score = stringFeature[i].score,
                release_date = stringFeature[i].release_date,
                overview = stringFeature[i].overview,
                backDrop = stringFeature[i].backDrop,
                posterPath = stringFeature[i].posterPath,
                iconId = R.drawable.ic_videocam,
                lightColor = Color(lightColor),
                mediumColor = Color(mediumColor),
                darkColor = Color(darkColor)
            )
            featureSection.add(feature)
        }
    }







    mainActivity.BackPressHandler(onBackPressed = onBack)
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ){
        Column {
            GreetingSection(salam,harapan,date)
            ChipSection(chips = stringButton)
            CurrentMeditation(data,email)
            FeatureSection(
                stringFeature,
                features = featureSection,
                onLastItemVisible = {
                    lastItemVisible = true
                }
            )
        }
        BottomMenu(
            editor = editor,
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

            mainActivity.getPopularApi(page++,mainTransport,context,"Halaman 2 ",stringFeature)

        }
    }
}

fun adjustLightness(hue: Int, lightnessVariation: Float, lightness: Float): Int {
    val saturation = 1.0f // Nilai saturasi yang tetap
    val value = lightness * lightnessVariation // Mengatur variasi kecerahan sesuai dengan lightnessVariation
    return android.graphics.Color.HSVToColor(floatArrayOf(hue.toFloat(), saturation, value))
}


@Composable
fun BottomMenu(
    editor: Editor?,
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
                selectedItemIndex = index
                if (selectedItemIndex == 0){
                    navController?.navigate(ScreenRoute.HomeScreen.route)
                }else if (selectedItemIndex == 1){
                    navController?.navigate(ScreenRoute.SecondScreen.withArgs(selectedItemIndex.toString()))
                }else if(selectedItemIndex == 4){



                    editor?.clear()
                    editor?.apply()
                    navController?.navigate(ScreenRoute.SplashScreen.route)

                }
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

@Composable
fun GreetingSection(
    greeting: String,
    wish: String,
    date: String
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ){
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
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription ="Search",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ChipSection(
    chips: List<String>
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
                        Util.toastToText(context,""+chips[selectedChipIndex])
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
fun CurrentMeditation(
    data: String,
    email: String
){
    val contex = LocalContext.current
//    BoxWithConstraints(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .padding(15.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(Red3)
//            .padding(horizontal = 15.dp, vertical = 20.dp).fillMaxWidth().height(40.dp)
//    ){
//        val width = constraints.maxWidth
//        val height = constraints.maxHeight
//
//        //Medium colored path
//        val mediumColoredPoint1 = Offset(0f,height * 0.3f)
//        val mediumColoredPoint2 = Offset(width * 0.1f,height * 0.35f)
//        val mediumColoredPoint3 = Offset(width * 0.4f,height * 0.05f)
//        val mediumColoredPoint4 = Offset(width *0.7f,height * 0.6f)
//        val mediumColoredPoint5 = Offset(width * 1.4f,-height.toFloat())
//
//        val mediumColoredPath = Path().apply {
//            moveTo(mediumColoredPoint1.x,mediumColoredPoint2.y)
//            standardQuadFromTo(mediumColoredPoint1,mediumColoredPoint2)
//            standardQuadFromTo(mediumColoredPoint2,mediumColoredPoint3)
//            standardQuadFromTo(mediumColoredPoint3,mediumColoredPoint4)
//            standardQuadFromTo(mediumColoredPoint4,mediumColoredPoint5)
//            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
//            lineTo(-100f, height.toFloat() + 100f)
//            close()
//        }
//
//        // Light colored path
//        val lightPoint1 = Offset(0f, height * 0.35f)
//        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
//        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
//        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
//        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)
//
//        val lightColoredPath = Path().apply {
//            moveTo(lightPoint1.x, lightPoint1.y)
//            standardQuadFromTo(lightPoint1, lightPoint2)
//            standardQuadFromTo(lightPoint2, lightPoint3)
//            standardQuadFromTo(lightPoint3, lightPoint4)
//            standardQuadFromTo(lightPoint4, lightPoint5)
//            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
//            lineTo(-100f, height.toFloat() + 100f)
//            close()
//        }
//
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//        ){
//            drawPath(
//                path = mediumColoredPath,
//                color = Red2
//            )
//            drawPath(
//                path = lightColoredPath,
//                color = Red1
//            )
//        }
//    }

    val gradientColors = listOf(

        Blue1,
        Red1,
        OrangeYellow1,
        Green1

    )

    val gradient = Brush.verticalGradient(gradientColors)

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
            painter = painterResource(R.drawable.ic_play),
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    Util.toastToText(contex,"Music Play")
                }
        )
    }

}

@Composable
fun FeatureSection(
    stringFeature: MutableList<Movies>,
    features: List<Feature>,
    onLastItemVisible: () -> Unit
){
    val context = LocalContext.current
    val mainActivity = MainActivity()
    val mainTransport = MainTransport()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Features",
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
            Column(
            ) {
                Text(
                    text=feature.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 12.sp),
                    lineHeight = 26.sp,
                    modifier = Modifier
                        .align(Alignment.Start).fillMaxWidth()//topstart
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    text=feature.release_date,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 10.sp, fontWeight = FontWeight.Thin),
                    lineHeight = 26.sp,
                    modifier = Modifier
                        .align(Alignment.End).fillMaxWidth()
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
                    containerColor = DeepBlue,
                    onDismissRequest = { alertDialogOpen.value = false },
                    title = { Text(text = feature.title) },
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
            Text(overview)
        }
        item {
            ImageViewByUrl("https://image.tmdb.org/t/p/original/$backdrop")
        }
    }
}

@Composable
fun ImageViewByUrl(url: String) {
    val painter = rememberImagePainter(
        data = url
//        builder = {
//            transformations(CircleCropTransformation())
//        }
    )

    Image(
        painter = painter,
        contentDescription = null, // Optional content description
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(300.dp)
    )
}
