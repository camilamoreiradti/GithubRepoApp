package com.example.githubrepoapp.presentation.repolist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RepoListScreen(
    onNavigateToRepoItem: (id: Long) -> Unit,
) {
    RepoListContent()
}

@Composable
fun RepoListContent() {
    Text("List Screen")
}