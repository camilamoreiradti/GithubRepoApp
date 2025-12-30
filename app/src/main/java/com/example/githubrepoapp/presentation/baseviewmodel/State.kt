package com.example.githubrepoapp.presentation.baseviewmodel

sealed class State<out T> {
    object Loading : State<Nothing>()
    object Error : State<Nothing>()
    data class Success<T>(
        val data: T
    ) : State<T>()
}