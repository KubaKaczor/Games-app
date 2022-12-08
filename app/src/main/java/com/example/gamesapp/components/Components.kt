package com.example.gamesapp.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.gamesapp.R
import com.example.gamesapp.model.RedditPost
import com.example.gamesapp.model.TypeWIthId
import com.example.gamesapp.navigation.Screens
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun GamesAppBar(
    title: String,
    icon: ImageVector? = null,
    isHome: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
){
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if(isHome) {
                    Icon(
                        imageVector = Icons.Filled.Gamepad,
                        contentDescription = "Logo description",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f)
                    )
                }
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = "arrow back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }
                Spacer(modifier = Modifier.width(40.dp) )
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )

            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screens.SearchScreen.name)
            }) {
                if (isHome) Row() {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search icon",
                        // tint = Color.Green.copy(alpha = 0.4f)
                    )
                } else Box {}


            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp)

}

@Composable
fun GameCard(
    game: com.example.gamesapp.model.Game,
    navController: NavController
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 4.dp, bottom = 4.dp)
            .clickable {
                navController.navigate(Screens.InfoScreen.name + "/${game.id}")
            },
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp
    ) {
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
            }

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
                        modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 0.dp, end = 6.dp),
                        text = game.name,
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 0.dp, bottom = 6.dp, end = 6.dp),
                        text = if(game.released != null) game.released else "Release date unknown",
                        style = TextStyle(color = Color.White, fontSize = 16.sp),
                        fontSize = 14.sp
                    )
                }
            }

            Box(
                contentAlignment = Alignment.TopEnd
            ){
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, top = 4.dp, bottom = 0.dp, end = 6.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color.Black)
                        .padding(8.dp),
                    text = game.rating.toString(),
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    fontSize = 14.sp
                )
            }
    }
}

@Composable
fun ScreenshotCard(image: com.example.gamesapp.model.Result){
    
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(200.dp)
            .padding(top = 4.dp, bottom = 4.dp, end = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp
    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ){
            Box(
                modifier = Modifier.height(200.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = image.image),
                    contentDescription = "game image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun PostCard(post: RedditPost){

    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(post.url)) }

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(300.dp)
            .padding(top = 4.dp, bottom = 4.dp, end = 8.dp, start = 4.dp)
            .clickable {
                       context.startActivity(intent)
            },
        //border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(16.dp),
        elevation = 32.dp
    ) {
        Column(
            modifier = Modifier
                .width(screenWidth.dp - (spacing * 2))
                .verticalScroll(
                    rememberScrollState()
                ),
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = R.drawable.reddit_logo),
                        contentDescription = "reddit logo",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = post.name,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
            Text(
                text = post.username,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(8.dp)
            )

            val postDesc = HtmlCompat.fromHtml(post.text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            Text(
                text = postDesc,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body1
            )

            if(!post.image.isNullOrEmpty()){
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(250.dp)
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally),

                ) {
                    Image(
                        painter = rememberImagePainter(data = post.image),
                        contentDescription = "reddit image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    placeholder: String,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
){
    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it },
        label = {Text(text=labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        placeholder = { Text(text = placeholder)},
        shape = CircleShape
    )
}

@Composable
fun CollapsingEffectScreen(gameDetails: com.example.gamesapp.model.GameDetails) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            Image(
                painter = rememberImagePainter(data = gameDetails.backgroundImage),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(240.dp)
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
fun CollapsingToolbarWithContent(
    image: String,
    title: String,
    navController: NavController,
    content: @Composable() () -> Unit
){
    val state = rememberCollapsingToolbarScaffoldState()
    val offsetY = state.offsetY // y offset of the layout
    val progress = state.toolbarState.progress // how much the toolbar is expanded (0: collapsed, 1: expanded)

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .pin()
            ){
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .pin(),
                    painter = rememberImagePainter(data = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }


            if (progress == 0f || progress == 1f) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrow back",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .road(Alignment.TopStart, Alignment.TopStart)
                        .padding(8.dp, 16.dp, 16.dp, 16.dp))
            }

            Text(
                text = title,
                modifier = Modifier
                    .road(Alignment.BottomStart, Alignment.BottomEnd)
                    .padding(48.dp, 16.dp, 16.dp, 16.dp),
                color = Color.White,
                fontSize = textSize
            )
        }
    ){
        content()
    }
}

@Composable
fun ExpandedCard(
    title: String,
    defaultValue: Boolean,
    content: @Composable () -> Unit
){

    val detailsExpanded = remember { mutableStateOf(defaultValue) }

    Card(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 2.dp)
            .fillMaxWidth()
            .clickable {
                detailsExpanded.value = !detailsExpanded.value
            },
        elevation = 24.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = if(!detailsExpanded.value) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropUp,
                    contentDescription = "expand details arrow",
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            AnimatedVisibility(visible = detailsExpanded.value){
                content()
            }
        }

    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp
        )


        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DetailRowWithList(
    label: String,
    values: List<TypeWIthId>,
    type: String,
    navController: NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = label,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 18.sp
        )

        Column(
            horizontalAlignment = Alignment.End
        ) {
            for(i in values){
                Text(
                    text = i.name,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.GamesScreen.name+"/$type/${i.id}/${i.name}")
                    }
                )
            }
        }
    }
}

@Composable
fun ZoomableImage(image: String) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize() // Give the size you want...
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                    rotationZ = rotationState.value
                ),
            contentDescription = null,
            painter = rememberImagePainter(data = image)
        )
    }
}