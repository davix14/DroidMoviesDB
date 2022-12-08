package com.example.droidmoviesdb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.droidmoviesdb.ui.components.RegisterForm
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginView()
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(paddingValues: PaddingValues = PaddingValues(), navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(

        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.login_Title),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                var usernameText by rememberSaveable { mutableStateOf("") }

                OutlinedTextField(
                    value = usernameText,
                    onValueChange = { usernameText = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "") }
                )
                Spacer(modifier = Modifier.height(24.dp))
                var passwordText by rememberSaveable { mutableStateOf("") }

                OutlinedTextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    placeholder = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "") }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    OutlinedButton(onClick = {
                        passwordText = ""
                        usernameText = ""
                    }) {
                        Text(
                            "Clear",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    val context = LocalContext.current
                    OutlinedButton(onClick = { context.startActivity(
                        Intent(context, SaveSearchResultActivity::class.java)
                    ) }) {
                        Text(
                            "Submit",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { navController.navigate("registerForm") }) {
                        Text(
                            "Register",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
fun LoginView() {
    val navController = rememberNavController()
    DroidMoviesDBTheme {
        Scaffold(
            topBar = { TopAppBar() },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inversePrimary)
        ) {
            val paddin = it
            NavHost(navController = navController, startDestination = "loginForm") {
                composable("loginForm") { LoginForm(paddin, navController) }
                composable("registerForm") { RegisterForm().RegisterFormView() }
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640, showBackground = true, backgroundColor = 0x9ea39f)
@Composable
fun DefaultPreview() {
    LoginView()
}