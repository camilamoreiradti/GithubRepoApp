package com.example.githubrepoapp.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.components.LoadingIndicator

@Composable
fun SplashScreen(
    toSignIn: () -> Unit,
    toListScreen: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAppStart()
    }

    when (val state = uiState) {
        State.Error -> {
            toSignIn()
        }

        State.Loading -> {
            LoadingIndicator()
        }

        is State.Success<*> -> {
            toListScreen()
        }
    }
}