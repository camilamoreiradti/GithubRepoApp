package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.githubrepoapp.presentation.components.LoadingIndicator
import com.example.githubrepoapp.presentation.repoitem.RepoItemScreen
import com.example.githubrepoapp.presentation.repolist.RepoListScreen
import com.example.githubrepoapp.presentation.splash.AuthState
import com.example.githubrepoapp.presentation.splash.SplashViewModel
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object ListRoute

@Serializable
data class RepoItemRoute(val ownerName: String, val repoName: String)

@Composable
fun MainNavHost(
    toSignIn: () -> Unit,
    splashViewModel: SplashViewModel
) {
    val navController = rememberNavController()
    val authState by splashViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(ListRoute) {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            }

            is AuthState.Unauthenticated -> {
                toSignIn()
            }

            else -> {}
        }
    }

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            LoadingIndicator()
        }

        composable<ListRoute> {
            RepoListScreen(
                toSignIn = toSignIn,
                onNavigateToRepoItem = { ownerName, repoName ->
                    navController.navigate(
                        RepoItemRoute(
                            ownerName = ownerName,
                            repoName = repoName,
                        )
                    )
                }
            )
        }

        composable<RepoItemRoute> { backStackEntry ->
            // NavBackStackEntry: recupera os parâmetros da tela anterior, ex: id
            // Converte argumentos da URL de volta para objeto tipado
            val repoItemRoute = backStackEntry.toRoute<RepoItemRoute>()
            RepoItemScreen(
                ownerName = repoItemRoute.ownerName,
                repoName = repoItemRoute.repoName,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}