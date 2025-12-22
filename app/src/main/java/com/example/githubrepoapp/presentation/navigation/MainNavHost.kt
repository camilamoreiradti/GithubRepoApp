package com.example.githubrepoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.githubrepoapp.presentation.repoitem.RepoItemScreen
import com.example.githubrepoapp.presentation.repolist.RepoListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListRoute

@Serializable
data class RepoItemRoute(val id: Long)

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListRoute) {
        composable<ListRoute> {
            RepoListScreen(
                onNavigateToRepoItem = { id ->
                    navController.navigate(RepoItemRoute(id = id))
                }
            )
        }

        composable<RepoItemRoute> { backStackEntry ->
            // NavBackStackEntry: recupera os parâmetros da tela anterior, ex: id
                // Converte argumentos da URL de volta para objeto tipado
            val repoItemRoute = backStackEntry.toRoute<RepoItemRoute>()
            RepoItemScreen(
                id = repoItemRoute.id,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}