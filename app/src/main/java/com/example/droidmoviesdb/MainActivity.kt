package com.example.droidmoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.droidmoviesdb.ui.SingleSearchResult
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
                var searchBoxText by remember { mutableStateOf("") }
                var isSearchVisible by remember { mutableStateOf(false) }
                OutlinedButton(onClick = { isSearchVisible = !isSearchVisible }) {
                    Text("Search")
                }
                Spacer(Modifier.width(16.dp))
                if (isSearchVisible) {
                    OutlinedTextField(
                        value = searchBoxText,
                        onValueChange = { searchBoxText = it })
                }
            }
        }
    }

    @Composable
    fun SearchResults() {
        LazyColumn {
            // TODO Add iterator over List of search results
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SingleResult(singleSearchResult: SingleSearchResult) {
        Card {
            Column {
                Text(
                    singleSearchResult.title,
                    style = MaterialTheme.typography.headlineLarge
                )
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

    val sampleSingleResult = SingleSearchResult(
        "Split",
        "2016",
        "tt4972582",
        "movie",
        "https://m.media-amazon.com/images/M/MV5BZTJiNGM2NjItNDRiYy00ZjY0LTgwNTItZDBmZGRlODQ4YThkL2ltYWdlXkEyXkFqcGdeQXVyMjY5ODI4NDk@._V1_SX300.jpg"
    )

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun SingleResultPreview() {
        SingleResult(singleSearchResult = sampleSingleResult)
    }

}