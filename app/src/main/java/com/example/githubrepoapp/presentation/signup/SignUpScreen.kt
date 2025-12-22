package com.example.githubrepoapp.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubrepoapp.presentation.components.AuthButton
import com.example.githubrepoapp.presentation.components.AuthFormFields
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {
    SignUpContent(onNavigateToLogIn)
}

@Composable
fun SignUpContent(
    onNavigateToLogIn: () -> Unit
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var confirmPasswordText by remember { mutableStateOf("") }

    // API do compose para controlar programaticamente o foco entre os elementos da UI
    val focusManager = LocalFocusManager.current

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "Sign Up",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(50.dp))

            AuthFormFields(
                email = emailText,
                onEmailChange = {  newValue -> emailText = newValue },
                password = passwordText,
                onPasswordChange = { passwordText = it },
                confirmPassword = confirmPasswordText,
                onConfirmPasswordChange = {  newValue -> confirmPasswordText = newValue },
                onSubmit = { }
            )

            Spacer(Modifier.height(12.dp))

            AuthButton(
                text = "Sign In",
                email = emailText,
                password = passwordText,
                enabled = emailText.isNotBlank()
                        && passwordText.isNotBlank()
                        && confirmPasswordText.isNotBlank(),
                onClick = { }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                buildAnnotatedString {
                    append("Have an account already? ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "signup",
                            styles = TextLinkStyles(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ),
                            linkInteractionListener = { onNavigateToLogIn() }
                        )
                    ) {
                        append("Log In")
                    }
                }
            )
            Spacer(Modifier.height(40.dp))

            Column(Modifier.padding(horizontal = 50.dp)) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text("Continue with Google")
                }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                ) {
                    Text("Continue with Facebook")
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {
    GithubRepoAppTheme() {
        SignUpContent(
            onNavigateToLogIn = { }
        )
    }
}