package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.githubrepoapp.presentation.login.LoginScreen
import com.example.githubrepoapp.presentation.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object SignUpRoute

@Composable
fun AuthNavHost(
    onAuthSuccess: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginSuccess = onAuthSuccess,
                onNavigateToSignUp = { navController.navigate(SignUpRoute) }
            )
        }

        composable<SignUpRoute> {
            SignUpScreen(
                onSignUpSuccess = onAuthSuccess,
                onNavigateToLogIn = { navController.navigate(LoginRoute) }
            )
        }
    }
}