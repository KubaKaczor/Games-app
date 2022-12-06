package com.example.gamesapp.screens.info

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.example.gamesapp.components.*
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.*
import com.example.gamesapp.navigation.Screens
import com.example.gamesapp.screens.search.SearchContent
import com.example.gamesapp.utils.getStoreImageIdByName
import me.onebone.toolbar.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun InfoScreen(navController: NavController, gameId: String, viewModel: InfoViewModel = hiltViewModel()){

    val gameInfo = produceState<Resource<com.example.gamesapp.model.GameDetails>>(initialValue = Resource.Loading()){
        value = viewModel.getGameInfo(gameId)
    }.value

    if(gameInfo.data == null){

        CircularProgressIndicator()
    }else{
        CollapsingToolbarWithContent(
            image = gameInfo.data!!.backgroundImage,
            title = gameInfo.data!!.name,
            navController = navController
        ) {
            InfoContent(navController = navController, viewModel = viewModel, gameDetails = gameInfo.data!!)
        }
    }
}


@Composable
fun InfoContent(navController: NavController, viewModel: InfoViewModel, gameDetails: GameDetails){
    var imagesCache = remember {
        listOf<com.example.gamesapp.model.Result>()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)) {
            ExpandedCard(title = "Game details", defaultValue = true) {
                GameDetails(gameDetails = gameDetails, navController = navController)
            }
            ExpandedCard(title = "Description", defaultValue = true) {
                Column() {
                    Text(
                        text = gameDetails.descriptionRaw,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            ExpandedCard(title = "Images", defaultValue = false) {
                if (imagesCache.isEmpty()) {
                    val screenshots =
                        produceState<Resource<List<com.example.gamesapp.model.Result>>>(
                            initialValue = Resource.Loading()
                        ) {
                            value = viewModel.getGameScreenshots(gameDetails.id.toString())
                        }.value

                    if (screenshots.data == null)
                        CircularProgressIndicator()
                    else {
                        imagesCache = screenshots.data!!
                    }
                }
                ScreenShots(images = imagesCache)
            }

            ExpandedCard(title = "Requirements", defaultValue = false) {
                RequirementsSection(gameDetails = gameDetails)
            }

            ExpandedCard(title = "Series", defaultValue = false) {
                SeriesSection(gameId = gameDetails.id, viewModel = viewModel, navController = navController)
            }

            ExpandedCard(title = "Reddit", defaultValue = false) {
                RedditSection(gameId = gameDetails.id, viewModel = viewModel)
            }

            ExpandedCard(title = "Buy game", defaultValue = false) {
                BuyGameSection(gameId = gameDetails.id, viewModel = viewModel)
            }
        }

        
    }
}

@Composable
fun RequirementsSection(gameDetails: GameDetails) {

    var minimum = remember {
        mutableStateOf("")
    }
    var recommended = remember {
        mutableStateOf("")
    }

    for (platform in gameDetails.platforms) {
        if (platform.platform.name == "PC") {
            if (!platform.requirements.minimum.isNullOrEmpty())
                minimum.value = platform.requirements.minimum
            if (!platform.requirements.recommended.isNullOrEmpty())
                recommended.value = platform.requirements.recommended
        }
    }
    if (minimum.value.isEmpty()) {
        minimum.value = "Minimum requirements not found"
    }
    if (recommended.value.isEmpty()) {
        recommended.value = "recommended requirements not found"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = minimum.value)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = recommended.value)
    }
}

@Composable
fun SeriesSection(gameId: Int, viewModel: InfoViewModel, navController: NavController) {
    val seriesGames = produceState<Resource<List<Game>>>(
        initialValue = Resource.Loading()
    ){
        value = viewModel.getGamesFromSeries(gameId.toString())
    }.value

    if(seriesGames.data == null){
        CircularProgressIndicator()
    }else{
        Row(
            modifier = Modifier
                .padding(top = 8.dp, end = 8.dp, bottom = 8.dp, start = 4.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            if (seriesGames.data!!.isEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(
                        text = "No other games from this series found",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                for (game in seriesGames.data!!) {
                    GameSeriesCard(game = game, navController = navController)
                }
            }
        }
    }
}

@Composable
fun GameSeriesCard(game: Game, navController: NavController){

    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(4.dp)
            .clickable {
                navController.navigate(Screens.InfoScreen.name + "/${game.id}")
            },
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp
    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2))
        ){
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = game.backgroundImage),
                    contentDescription = "game image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = Color.Black))  {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = game.name,
                            style = TextStyle(color = Color.White, fontSize = 16.sp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RedditSection(gameId: Int, viewModel: InfoViewModel) {
    val posts = produceState<Resource<List<RedditPost>>>(
        initialValue = Resource.Loading()
    ){
        value = viewModel.getRedditPosts(gameId.toString())
    }.value

    if(posts.data == null){
        CircularProgressIndicator()
    }else{
        Row(
            modifier = Modifier
                .padding(top = 8.dp, end = 8.dp, bottom = 8.dp, start = 4.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            if (posts.data!!.isEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(
                        text = "No posts found",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                for (post in posts.data!!) {
                    PostCard(post)
                }
            }
        }
    }
}

@Composable
fun BuyGameSection(gameId: Int, viewModel: InfoViewModel) {
    val sites =
        produceState<Resource<List<BuySite>>>(
            initialValue = Resource.Loading()
        ) {
            value = viewModel.getSitesToBuyGame(gameId.toString())
        }.value

    if(sites.data == null){
        CircularProgressIndicator(modifier = Modifier.then(Modifier.size(32.dp))
        )
    }
    else{
        if(sites.data!!.isNotEmpty()){
            val context = LocalContext.current
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                for(site in sites.data!!){
                    BuySiteRow(site = site.url, context = context)
                }
            }
        }else{
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No stores found",
                    style = TextStyle(
                        color = Color.Red.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

@Composable
fun BuySiteRow(site: String, context: Context){
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(site)) }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = site,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .clickable {
                        context.startActivity(intent)
                    }
            )
        }

        val image = getStoreImageIdByName(site)
        if(image > -1){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalArrangement = Arrangement.Center,

                ) {
                Image(
                    painterResource(image),
                    contentDescription = "storeLogo",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}


@Composable
fun ScreenShots(images: List<com.example.gamesapp.model.Result>) {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
            if (images.isEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(
                        text = "No images found",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                for (screenshot in images) {
                    ScreenshotCard(screenshot)
                }
            }
    }
}

@Composable
fun GameDetails(gameDetails: com.example.gamesapp.model.GameDetails, navController: NavController){

    Column() {
        DetailRow(label = "Title:", value = gameDetails.name)

        val genres = mutableListOf<TypeWIthId>()
        gameDetails.genres.forEach { g -> genres.add(TypeWIthId(g.name, g.id.toString())) }

        DetailRowWithList(label = "Genre:", values = genres, type = "genre", navController = navController)
        DetailRow(label = "Released:", value = if(gameDetails.released != null) gameDetails.released else "Unknown")

        val devs = mutableListOf<TypeWIthId>()
        gameDetails.developers.forEach { d -> devs.add(TypeWIthId(d.name, d.id.toString())) }
        DetailRowWithList(label = "Developers:", values = devs, type = "dev", navController = navController)

        val platforms = mutableListOf<TypeWIthId>()
        gameDetails.parentPlatforms.forEach { d -> platforms.add(TypeWIthId(d.platform.name, d.platform.id.toString())) }
        DetailRowWithList(label = "Platforms:", values = platforms, type = "platform", navController = navController)

    }
}