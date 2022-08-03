package com.example.droidmoviesdb

import SingleSearchResult
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.droidmoviesdb.search.ExpandableCardModel
import com.example.droidmoviesdb.search.SavedEntriesCardsViewModel
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme

class MainActivity : ComponentActivity() {
    private val savedEntriesCardsViewModel by viewModels<SavedEntriesCardsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityView(savedEntriesCardsViewModel)
        }
    }

    @Composable
    fun HomeScreen(savedEntriesCardsViewModel: SavedEntriesCardsViewModel) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                SearchComponent()
            }
            SavedEntriesComponent(savedEntriesCardsViewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SavedEntriesComponent(savedEntriesCardsViewModel: SavedEntriesCardsViewModel) {
        val sortingText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Item Count: ")
            }
            append(sampleResults.count().toString())
            append("  ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Sorted By: ")
            }
            append("TODO!")
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Info and Sort Card
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondary,
                                    RoundedCornerShape(2.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(sortingText)
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
//                            .padding(top = 8.dp)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(3.dp))
//                            .padding(8.dp)
                    ) {
                        val cards by savedEntriesCardsViewModel.cards.collectAsState()
                        val expandedCardIds by savedEntriesCardsViewModel.expandedCardIdsList.collectAsState()
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            itemsIndexed(cards) { _, card ->
                                ExpandableCard(
                                    card = card,
                                    onCardArrowClick = { savedEntriesCardsViewModel.onCardArrowClicked(card.id) },
                                    expanded = expandedCardIds.contains(card.id),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun ExpandableContent(
        visible: Boolean = true,
        initialVisibility: Boolean = false
    ) {
        val EXPANSION_TRANSITION_DURATION = 300

        val enterTransition = remember {
            expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(EXPANSION_TRANSITION_DURATION)
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(EXPANSION_TRANSITION_DURATION)
            )
        }
        val exitTransition = remember {
            shrinkVertically(
                // Expand from the top.
                shrinkTowards = Alignment.Top,
                animationSpec = tween(EXPANSION_TRANSITION_DURATION)
            ) + fadeOut(
                // Fade in with the initial alpha of 0.3f.
                animationSpec = tween(EXPANSION_TRANSITION_DURATION)
            )
        }
        AnimatedVisibility(
            visible = visible,
            initiallyVisible = initialVisibility,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.heightIn(100.dp))
                Text(
                    text = "Expandable content here",
                    textAlign = TextAlign.Center
                )
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ExpandableCard(
        card: ExpandableCardModel,
        onCardArrowClick: () -> Unit,
        expanded: Boolean,
    ) {
        val EXPAND_ANIMATION_DURATION = 300
        val transitionState = remember {
            MutableTransitionState(expanded).apply {
                targetState = !expanded
            }
        }
        val transition = updateTransition(transitionState)
        val cardPaddingHorizontal by transition.animateDp({
            tween(durationMillis = EXPAND_ANIMATION_DURATION)
        }, label = "") {
            if (expanded) 48.dp else 24.dp
        }
        val cardRoundedCorners by transition.animateDp({
            tween(
                durationMillis = EXPAND_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        }, label = "") {
            if (expanded) 0.dp else 16.dp
        }
        val arrowRotationDegree by transition.animateFloat({
            tween(durationMillis = EXPAND_ANIMATION_DURATION)
        }, label = "") {
            if (expanded) 0f else 180f
        }

        Card(
            shape = RoundedCornerShape(cardRoundedCorners),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = cardPaddingHorizontal,
                    vertical = 8.dp
                )
        ) {
            Column {
                Box {
                    CardArrow(
                        degrees = arrowRotationDegree,
                        onClick = onCardArrowClick
                    )
                    CardTitle(title = card.savedEntry.title)
                }
                ExpandableContent(visible = expanded, initialVisibility = expanded)
            }
        }
    }

    @Composable
    fun CardTitle(title: String) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun CardArrow(
        degrees: Float,
        onClick: () -> Unit
    ) {
        IconButton(
            onClick = onClick,
            content = {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.KeyboardArrowUp),
                    contentDescription = "Expandable Arrow",
                    modifier = Modifier.rotate(degrees),
                )
            }
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchComponent() {
        var searchBoxText by remember { mutableStateOf("") }
        var isSearchVisible by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    OutlinedButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Text("Search")
                    }
                    Spacer(Modifier.width(16.dp))
                    if (isSearchVisible) {
                        OutlinedTextField(
                            value = searchBoxText,
                            onValueChange = { searchBoxText = it }
                        )
                    }
                }
                if (isSearchVisible) {
                    Spacer(Modifier.height(8.dp))
                    SearchResults(sampleResults)
                }
            }
        }
    }

    @Composable
    fun SearchResults(searchResults: List<SingleSearchResult>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = searchResults, itemContent = { item ->
                SingleResult(singleSearchResult = item)
                Spacer(Modifier.height(16.dp))
            })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SingleResult(singleSearchResult: SingleSearchResult) {
        Card {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(singleSearchResult.poster)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        singleSearchResult.title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        singleSearchResult.type,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        singleSearchResult.year,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

        }
    }

    @Composable
    fun TopAppBar() {
        SmallTopAppBar(
            title = {
                Text(
                    stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            colors = TopAppBarDefaults
                .smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainActivityView(savedEntriesCardsViewModel: SavedEntriesCardsViewModel) {
        DroidMoviesDBTheme {
            Scaffold(
                topBar = { TopAppBar() }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    HomeScreen(savedEntriesCardsViewModel)
                }
            }
        }
    }

    private val sampleResults = listOf<SingleSearchResult>(
        SingleSearchResult(
            "Split",
            "2016",
            "tt4972582",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BZTJiNGM2NjItNDRiYy00ZjY0LTgwNTItZDBmZGRlODQ4YThkL2ltYWdlXkEyXkFqcGdeQXVyMjY5ODI4NDk@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "Split Second",
            "1992",
            "tt0105459",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BY2I2ZjBmMWEtMTVkZC00OTVkLWE1MjktOTc0MDgwNzZhYmU3XkEyXkFqcGdeQXVyNjExODE1MDc@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "California Split",
            "1974",
            "tt0071269",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BNjk5MzNjMDctYTdhNS00OWI0LWI2MzUtMDI3ODYwMDc5M2E3XkEyXkFqcGdeQXVyMjI4MjA5MzA@._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "The Split",
            "2018–2022",
            "tt7631146",
            "series",
            "https://m.media-amazon.com/images/M/MV5BODhhNzhmNDYtMTQyYy00NjMzLWJkNTMtZDc1YjZmZDE4NTM4XkEyXkFqcGdeQXVyMTM1MTE1NDMx._V1_SX300.jpg"
        ),
        SingleSearchResult(
            "Banana Split",
            "2018",
            "tt7755856",
            "movie",
            "https://m.media-amazon.com/images/M/MV5BYTlkNWRjMDMtYmVlYS00NTlkLWI5OTUtZWY1YmZmNTNkZGFjXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg"
        )
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun SingleResultPreview() {
        Scaffold(modifier = Modifier.padding(32.dp)) { SingleResult(singleSearchResult = sampleResults[0]) }
    }

}