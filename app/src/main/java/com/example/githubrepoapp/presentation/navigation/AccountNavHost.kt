package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubrepoapp.presentation.account.AccountScreen
import kotlinx.serialization.Serializable

@Serializable
object AccountRoute

@Composable
fun AccountNavHost(
    onLogOut: () -> Unit,
    toRepoList: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AccountRoute) {
        composable<AccountRoute>{
            AccountScreen(
                toLogIn = onLogOut,
                navigateBack = toRepoList
            )
        }
    }
}