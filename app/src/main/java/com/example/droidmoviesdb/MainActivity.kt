package com.example.droidmoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import SingleSearchResult
import android.app.appsearch.SearchResults
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityView()
        }
    }

    @Composable
    fun HomeScreen() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                SearchComponent()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchComponent() {
        var searchBoxText by remember { mutableStateOf("") }
        var isSearchVisible by remember { mutableStateOf(false) }
        Box(modifier = Modifier.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

    @Preview(showBackground = true, showSystemUi = true)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainActivityView() {
        DroidMoviesDBTheme {
            Scaffold(
                topBar = { TopAppBar() }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    HomeScreen()
                }
            }
        }
    }

    val sampleResults = listOf<SingleSearchResult>(
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

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun SingleResultPreview() {
        SingleResult(singleSearchResult = sampleResults[0])
    }

}