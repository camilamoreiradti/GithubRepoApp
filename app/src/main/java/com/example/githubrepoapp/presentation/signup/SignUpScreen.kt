package com.example.githubrepoapp.presentation.signup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit
) {
    SignUpContent()
}

@Composable
fun SignUpContent() {
    Text("Sign Up")
}