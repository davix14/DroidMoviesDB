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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
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
import com.example.droidmoviesdb.ui.components.SearchComponent as search

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
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                search().SearchComponent()
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
                .padding(4.dp)
                .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Card(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
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
//                                .border(
//                                    1.dp,
//                                    MaterialTheme.colorScheme.secondary,
//                                    RoundedCornerShape(2.dp)
//                                )
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Text(
                                    text = sortingText,
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background)

                                )
                            }
                        }
                    }
                    // Saved Entries List card
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
//                            .padding(top = 8.dp)
//                            .border(1.dp, Color.LightGray, RoundedCornerShape(3.dp))
//                            .padding(8.dp)
                    ) {
                        val cards by savedEntriesCardsViewModel.cards.collectAsState()
                        val expandedCardIds by savedEntriesCardsViewModel.expandedCardIdsList.collectAsState()
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            itemsIndexed(cards) { _, card ->
                                ExpandableCard(
                                    card = card,
                                    onCardArrowClick = {
                                        savedEntriesCardsViewModel.onCardArrowClicked(
                                            card.id
                                        )
                                    },
                                    expanded = expandedCardIds.contains(card.id),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun ExpandableContent(
        visible: Boolean = true,
        initialVisibility: Boolean = false,
        savedEntry: SingleSearchResult
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
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(savedEntry.poster)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .crossfade(true)
                                .build()
                        ),
                        "poster"
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = savedEntry.title,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = savedEntry.year,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
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
            if (expanded) 24.dp else 16.dp
        }
        val cardRoundedCorners by transition.animateDp({
            tween(
                durationMillis = EXPAND_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            )
        }, label = "") {
            if (expanded) 8.dp else 16.dp
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
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                Box {
                    val cardTitle = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(card.savedEntry.title)
                        }
                        append("\t\t")
                        withStyle(
                            style = SpanStyle(fontStyle = FontStyle.Italic)
                        ) {
                            append(card.savedEntry.type)
                        }
                    }
                    CardArrow(
                        degrees = arrowRotationDegree,
                        onClick = onCardArrowClick
                    )
                    CardTitle(
                        title = cardTitle
                    )
                }
                ExpandableContent(
                    savedEntry = card.savedEntry,
                    visible = expanded,
                    initialVisibility = expanded
                )
            }
        }
    }

    @Composable
    fun CardTitle(title: AnnotatedString) {
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


}