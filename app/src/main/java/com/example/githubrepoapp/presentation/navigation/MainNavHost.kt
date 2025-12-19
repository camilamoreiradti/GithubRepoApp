package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.githubrepoapp.presentation.repodetail.RepoDetailScreen
import com.example.githubrepoapp.presentation.repolist.RepoListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class RepoDetailRoute(val id: Long)

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListRoute) {
        composable<ListRoute> {
            RepoListScreen(
                onNavigateToRepoDetail = { id ->
                    navController.navigate(RepoDetailRoute(id = id))
                }
            )
        }

        composable<RepoDetailRoute> { backStackEntry ->
            // NavBackStackEntry: recupera os parâmetros da tela anterior, ex: id
                // Converte argumentos da URL de volta para objeto tipado
            val repoDetailRoute = backStackEntry.toRoute<RepoDetailRoute>()
            RepoDetailScreen(
                id = repoDetailRoute.id,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}