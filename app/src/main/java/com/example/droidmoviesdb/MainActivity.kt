package com.example.droidmoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    Text("Hello")
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

}