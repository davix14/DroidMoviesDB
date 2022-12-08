package com.example.droidmoviesdb

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.droidmoviesdb.ui.components.SearchComponent as search
import com.example.droidmoviesdb.ui.components.Stepper
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.compose.composable


class SaveSearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComposable()
        }
    }
}

@Composable
fun StepOne(paddingValues: PaddingValues = PaddingValues()) {
    Column(
        Modifier.padding(paddingValues)
    ) {
       Row(
           modifier = Modifier.padding(vertical = 8.dp, horizontal = 25.dp)
       ) {
           Stepper().StepsProgressBar(numberOfSteps = 4, currentStep = 2)
       }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Hello!")
    }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable() {
    val navController = rememberNavController()
    DroidMoviesDBTheme {
        Scaffold(
            topBar = { SaveNewEntryTopAppBar() },
            modifier = Modifier
                .fillMaxSize()

        ) {
            val paddin = it
            NavHost(
                navController = navController,
                startDestination = "stepOneSearch"
            ) {
                composable("stepOneSearch") { StepOne(paddin) }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_4)
@Composable
fun DefaultPreview2() {
    MainComposable()
}