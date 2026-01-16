package com.example.githubrepoapp.utils

import java.util.regex.Pattern

fun String.isValidEmail(): Boolean {
    val pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 6
}