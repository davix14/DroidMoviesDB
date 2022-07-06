package com.example.droidmoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidMoviesDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginForm()
                }
            }
        }
    }
}

@Composable
fun Title(
    title: String
) {
    Text(
        title,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun UsernameTextField() {
    var usernameText by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = usernameText,
        onValueChange = { usernameText = it },
        label = { Text("Username") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "") }
    )
}

@Composable
fun PasswordTextField() {
    var passwordText by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = passwordText,
        onValueChange = { passwordText = it },
        placeholder = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm() {
    Card(

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Title(stringResource(R.string.login_Title))
            UsernameTextField()
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(widthDp = 360, heightDp = 640, showBackground = true, backgroundColor = 0x9ea39f)
@Composable
fun DefaultPreview() {
    DroidMoviesDBTheme {
        LoginForm()
    }
}