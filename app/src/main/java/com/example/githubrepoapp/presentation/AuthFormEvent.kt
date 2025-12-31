package com.example.githubrepoapp.presentation

sealed interface AuthFormEvent {
    data class EmailChanged(val email: String) : AuthFormEvent
    data class PasswordChanged(val password: String) : AuthFormEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthFormEvent
    data object Submit : AuthFormEvent
}