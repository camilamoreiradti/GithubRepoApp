package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.githubrepoapp.presentation.repoitem.RepoItemScreen
import com.example.githubrepoapp.presentation.repolist.RepoListScreen
import com.example.githubrepoapp.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object ListRoute

@Serializable
data class RepoItemRoute(val ownerName: String, val repoName: String)

@Composable
fun MainNavHost(
    toSignIn: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashScreen(
                toSignIn = toSignIn,
                toListScreen = {
                    navController.navigate(ListRoute) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable<ListRoute> {
            RepoListScreen(
                toSignIn = {
                    navController.navigate(SplashRoute) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                },
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