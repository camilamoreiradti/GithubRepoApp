package com.example.githubrepoapp.presentation.login

import android.content.res.Configuration
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.baseviewmodel.UiEvent
import com.example.githubrepoapp.presentation.components.AuthButton
import com.example.githubrepoapp.presentation.components.AuthFormFields
import com.example.githubrepoapp.presentation.navigation.ListRoute
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val stateSuccess = (uiState as State.Success<LoginViewModel.User>).data

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is ListRoute -> {
                            onLoginSuccess()
                        }
                    }
                }

                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }

                is UiEvent.NavigateBack -> {
                }
            }
        }
    }

    LoginContent(
        email = stateSuccess.email,
        password = stateSuccess.password,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    snackBarHostState: SnackbarHostState,
    onEvent: (AuthFormEvent) -> Unit,
    onNavigateToSignUp: () -> Unit
) {

    // API do compose para controlar programaticamente o foco entre os elementos da UI
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
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
                text = "Welcome back",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(50.dp))

            AuthFormFields(
                email = email,
                onEmailChange = {
                    onEvent(AuthFormEvent.EmailChanged(it))
                },
                password = password,
                onPasswordChange = {
                    onEvent(AuthFormEvent.PasswordChanged(it))
                },
                onSubmit = { onEvent(AuthFormEvent.Submit) }
            )

            Spacer(Modifier.height(12.dp))

            AuthButton(
                text = "Log In",
                email = email,
                password = password,
                enabled = email.isNotBlank() && password.isNotBlank(),
                onClick = { onEvent(AuthFormEvent.Submit) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                buildAnnotatedString {
                    append("Don't have an account? ")
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
                            linkInteractionListener = { onNavigateToSignUp() }
                        )
                    ) {
                        append("Sign Up")
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun LoginPreview() {
    GithubRepoAppTheme(darkTheme = false, dynamicColor = false) {
        LoginContent(
            onEvent = { },
            onNavigateToSignUp = { },
            snackBarHostState = SnackbarHostState(),
            email = "",
            password = ""
        )
    }
}