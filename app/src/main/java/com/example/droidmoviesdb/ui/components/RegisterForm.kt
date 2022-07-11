package com.example.droidmoviesdb.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidmoviesdb.R
import com.example.droidmoviesdb.ui.theme.DroidMoviesDBTheme

class RegisterForm {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Form() {
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
                        stringResource(R.string.register_form_title),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    var firstNameText by rememberSaveable { mutableStateOf("") }

                    OutlinedTextField(
                        value = firstNameText,
                        onValueChange = { firstNameText = it },
                        label = { Text("First name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "") }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    var lastNameText by rememberSaveable { mutableStateOf("") }

                    OutlinedTextField(
                        value = lastNameText,
                        onValueChange = { lastNameText = it },
                        label = { Text("Last Name") },
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
                    var confirmPasswordText by rememberSaveable { mutableStateOf("") }

                    OutlinedTextField(
                        value = confirmPasswordText,
                        onValueChange = { confirmPasswordText = it },
                        placeholder = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "") }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row {
                        OutlinedButton(onClick = {
                            confirmPasswordText = ""
                            firstNameText = ""
                        }) {
                            Text(
                                "Clear",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { /*TODO*/ }) {
                            Text(
                                "Submit",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { /*TODO*/ }) {
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

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun RegisterFormView() {
        DroidMoviesDBTheme {
            Form()
        }
    }

}