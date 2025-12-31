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
import com.example.githubrepoapp.presentation.AuthFormEvent
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.baseviewmodel.UiEvent
import com.example.githubrepoapp.presentation.components.AuthButton
import com.example.githubrepoapp.presentation.components.AuthFormFields
import com.example.githubrepoapp.presentation.components.LoadingIndicator
import com.example.githubrepoapp.presentation.navigation.ListRoute
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {
    val viewModel: SignUpViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadScreen()

        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is ListRoute -> {
                            onSignUpSuccess()
                        }
                    }
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }

                is UiEvent.NavigateBack -> {

                }
            }
        }
    }

    when (uiState) {
        State.Error -> {}

        State.Loading -> {
            LoadingIndicator()
        }

        is State.Success -> {
            val stateSuccess = (uiState as State.Success).data

            SignUpContent(
                email = stateSuccess.email,
                password = stateSuccess.password,
                confirmPassword = stateSuccess.confirmPassword,
                snackbarHostState = snackbarHostState,
                onEvent = viewModel::onEvent,
                onNavigateToLogIn = onNavigateToLogIn,
            )
        }
    }
}

@Composable
fun SignUpContent(
    email: String,
    password: String,
    confirmPassword: String,
    onEvent: (AuthFormEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateToLogIn: () -> Unit
) {

    // API do compose para controlar programaticamente o foco entre os elementos da UI
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                text = "Sign Up",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(50.dp))

            AuthFormFields(
                email = email,
                onEmailChange = { onEvent(AuthFormEvent.EmailChanged(it)) },
                password = password,
                onPasswordChange = { onEvent(AuthFormEvent.PasswordChanged(it)) },
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { onEvent(AuthFormEvent.ConfirmPasswordChanged(it)) },
                onSubmit = { onEvent(AuthFormEvent.Submit) }
            )

            Spacer(Modifier.height(12.dp))

            AuthButton(
                text = "Sign In",
                email = email,
                password = password,
                enabled = email.isNotBlank()
                        && password.isNotBlank()
                        && confirmPassword.isNotBlank(),
                onClick = { onEvent(AuthFormEvent.Submit) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                buildAnnotatedString {
                    append("Have an account already? ")
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = "login",
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
            onNavigateToLogIn = {},
            email = "",
            password = "",
            confirmPassword = "",
            onEvent = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}