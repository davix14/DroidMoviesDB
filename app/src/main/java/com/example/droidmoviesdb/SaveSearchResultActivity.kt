package com.example.droidmoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.droidmoviesdb.ui.components.SearchComponent as search
import com.example.droidmoviesdb.ui.components.Stepper
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class SaveSearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidMoviesDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun MainSaveSearchResultView(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 20.dp
            )
        ) {
//            Stepper().StepsProgressBar(numberOfSteps = 3, currentStep = 2)
            Spacer(modifier = Modifier.height(18.dp))
            NavHost(
                navController = navController,
                startDestination = "stepOneSearch"
            ) {
                composable("stepOneSearch") {
                    search().SearchComponent()
                }
            }
        }
    }
}

@Composable
fun StepOneSearch() {
    Text("HELLO!")
}

@Composable
fun SaveNewEntryTopAppBar() {
    SmallTopAppBar(
        title = {
            Text(
                "New Entry",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults
            .smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 360, heightDp = 640, showBackground = true, backgroundColor = 0x9ea39f)
@Composable
fun DefaultPreview2() {

    DroidMoviesDBTheme {
       Scaffold(
           topBar = { SaveNewEntryTopAppBar() }
       ) {
           MainSaveSearchResultView(it)
        }
    }
}